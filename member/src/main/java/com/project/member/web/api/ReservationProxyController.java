package com.project.member.web.api;

import com.project.global.dto.CompleteReservationDto;
import com.project.global.dto.ReservationDto;
import com.project.global.dto.ReservationStatus;
import com.project.global.dto.form.ArrivalCheckForm;
import com.project.global.dto.form.CreateReservationForm;
import com.project.global.dto.form.ReservationListForm;
import com.project.member.client.ReservationApiClient;
import com.project.member.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReservationProxyController {
    private final ReservationApiClient reservationApiClient;
    private final CustomerService customerService;

    /**
     * 예약하기
     */
    @PostMapping("/customer/reservation/{storeId}")
    public String createReservation (@PathVariable Long storeId,
            @ModelAttribute CreateReservationForm form, Model model) {
        CompleteReservationDto reservationDto = reservationApiClient.createReservation(form, storeId);

        model.addAttribute(reservationDto);
        return "customer/reservation-complete";
    }

    /**
     * Customer가 예약 조회하기
     */
    @GetMapping("/customer/reservations/{customerId}")
    public String getCustomerReservations(@PathVariable Long customerId, Model model) {
        List<ReservationDto> reservationList = reservationApiClient.getCustomerReservations(customerId);
        model.addAttribute(reservationList);
        return "customer/reservation-list";
    }

    /**
     * Manager가 본인매장 예약 조회하기
     */
    @GetMapping("/manager/reservations/{managerId}")
    public String getManagerReservations(@PathVariable Long managerId, Model model) {
        List<ReservationListForm> reservations = reservationApiClient.getManagerReservations(managerId);
        model.addAttribute("reservations", reservations);
        return "manager/reservation-list";
    }

    /**
     * 예약 승인 상태 변환하기
     */
    @PostMapping("/manager/reservations/{reservationId}/status/{status}")
    public String updateReservationStatus(
            @PathVariable Long reservationId,
            @PathVariable ReservationStatus status,
            @RequestHeader(value = HttpHeaders.REFERER, required = false) String referer) {
        log.info("status: {}");
        reservationApiClient.updateReservationStatus(reservationId, status);

        if (referer != null) {
            return "redirect:" + referer;
        }

        return "manager/managerHome";
    }

}
