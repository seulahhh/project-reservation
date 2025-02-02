package com.project.member.client;

import com.project.global.dto.ReservationDto;
import com.project.global.dto.ReservationStatus;
import com.project.global.dto.form.ArrivalCheckForm;
import com.project.global.dto.form.CreateReservationForm;
import com.project.member.service.CustomerService;
import com.project.member.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

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
    public ReservationDto createReservation (CreateReservationForm form,
            Long storeId) {
        System.out.println(form.toString());
        form.setCustomerId(customerService.getCurrentCustomerId());
        form.setManagerId(managerService.getManagerIdFromStoreId(storeId));
        form.setStoreId(storeId);
        String email = SecurityContextHolder.getContext()
                                            .getAuthentication()
                                            .getName();
        ReservationDto res = webClient.post()
                                      .uri("/api/reservation")
                                      .header("Content-Type", "application" +
                                              "/json")
                                      .bodyValue(form)
                                      .retrieve()
                                      .bodyToMono(ReservationDto.class)
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
    public List<ReservationDto> getManagerReservations (Long managerId) {
        Long storeId = managerService.getStoreIdFromManagerId(managerId);
        List<ReservationDto> res = webClient.get()
                                            .uri("/api/manager/reservations" +
                                                         "/" + storeId)
                                            .retrieve()
                                            .bodyToFlux(ReservationDto.class)
                                            .collectList()
                                            .block();
        return res;
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
