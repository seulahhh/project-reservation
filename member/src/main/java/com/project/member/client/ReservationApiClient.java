package com.project.member.client;

import com.project.global.dto.CompleteReservationDto;
import com.project.global.dto.ReservationDto;
import com.project.global.dto.ReservationStatus;
import com.project.global.dto.form.ArrivalCheckForm;
import com.project.global.dto.form.CreateReservationForm;
import com.project.global.dto.form.KioskForm;
import com.project.global.dto.form.ReservationListForm;
import com.project.member.model.dto.CustomerDto;
import com.project.member.service.CustomerService;
import com.project.member.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationApiClient {
    private final WebClient webClient;
    private final ManagerService managerService;
    private final CustomerService customerService;

    /**
     * 예약하기
     * API 호출
     */
    public CompleteReservationDto createReservation (CreateReservationForm form,
            Long storeId) {
        System.out.println(form.toString());
        form.setCustomerId(customerService.getCurrentCustomerId());
        form.setManagerId(managerService.getManagerIdFromStoreId(storeId));
        log.info("managerId, from, reservation api client: {}", form.getManagerId());
        form.setStoreId(storeId);
        CompleteReservationDto res = webClient.post()
                                      .uri("/api/reservation")
                                      .header("Content-Type", "application" +
                                              "/json")
                                      .bodyValue(form)
                                      .retrieve()
                                      .bodyToMono(CompleteReservationDto.class)
                                      .block();
        return res;
    }

    /**
     * Customer가
     * 예약 조회하기 API 호출
     */
    public List<ReservationDto> getCustomerReservations (Long customerId) {
        List<ReservationDto> res = webClient.get()
                                            .uri("/api/customer/reservations" +
                                                         "/" + customerId)
                                            .retrieve()
                                            .bodyToFlux(ReservationDto.class)
                                            .collectList()
                                            .block();
        return res;
    }

    /**
     * Manager가 본인매장
     * 예약 조회하기 API 호출
     */
    public List<ReservationListForm> getManagerReservations (Long managerId) {
        Long storeId = managerService.getStoreIdFromManagerId(managerId);
        List<ReservationDto> res = webClient.get()
                                            .uri("/api/manager/reservations" +
                                                         "/" + storeId)
                                            .retrieve()
                                            .bodyToFlux(ReservationDto.class)
                                            .collectList()
                                            .block();
        assert res != null;
        List<ReservationListForm> reservationListForms = res.stream()
                                                            .map(dto -> {
                                                   CustomerDto customer =
                                                           customerService.getCustomerDtoFromId(dto.getCustomerId());
                                                   return ReservationListForm.builder()
                                                           .customerEmail(customer.getEmail())
                                                           .customerName(customer.getName())
                                                           .reservationTime(dto.getReservationTime())
                                                           .guestCount(dto.getGuestCount())
                                                           .id(dto.getId())
                                                           .status(dto.getReservationStatus())
                                                           .build();
                                               })
                                                            .toList();
        return reservationListForms;
    }

    /**
     * 예약 상태 변환하기 API 호출
     */
    public String updateReservationStatus (Long reservationId, ReservationStatus status) {
        return webClient.put()
                        .uri("/api/reservations/" + reservationId + "/status")
                        .bodyValue(status)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
    }

    /**
     * 도착 확인 체크 API 호출
     */
    public String updateArrivalStatus (ArrivalCheckForm form) {
        form.setCheckAt(LocalDateTime.now());
        return webClient.post()
                 .uri("/api/kiosk/reservations/arrival-check")
                 .bodyValue(form)
                 .retrieve()
                 .bodyToMono(String.class)
                 .block();
    }

    /**
     * 해당하는 매장에 대한 고객의 예약 내역 조회
     */
    public List<ReservationDto> getCustomerStoreReservations (
            KioskForm kioskForm) {
        return webClient.get()
                        .uri("/api/kiosk/store/+"+kioskForm.getStoreId()+"/reservations/"+customerService.getCustomerIdFromEmail(kioskForm.getEmail()))
                        .retrieve()
                        .bodyToFlux(ReservationDto.class)
                        .collectList()
                        .block();
    }
}
