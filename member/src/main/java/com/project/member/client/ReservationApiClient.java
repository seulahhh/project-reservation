package com.project.member.client;

import com.project.global.dto.CompleteReservationDto;
import com.project.global.dto.ReservationDto;
import com.project.global.dto.ReservationStatus;
import com.project.global.dto.form.ArrivalCheckForm;
import com.project.global.dto.form.CreateReservationForm;
import com.project.global.dto.form.ReservationListForm;
import com.project.member.model.dto.CustomerDto;
import com.project.member.persistence.entity.Customer;
import com.project.member.service.CustomerService;
import com.project.member.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

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
        return webClient.post()
                 .uri("/api/reservations/arrival-check")
                 .bodyValue(form)
                 .retrieve()
                 .bodyToMono(String.class)
                 .block();
    }

}
