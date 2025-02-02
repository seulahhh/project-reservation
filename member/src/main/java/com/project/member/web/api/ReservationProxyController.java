package com.project.member.web.api;

import com.project.global.dto.ReservationDto;
import com.project.global.dto.ReservationStatus;
import com.project.global.dto.form.ArrivalCheckForm;
import com.project.global.dto.form.CreateReservationForm;
import com.project.member.client.ReservationApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReservationProxyController {
    private final ReservationApiClient reservationApiClient;

    /**
     * 예약하기
     */
    @PostMapping("/reservation/{storeId}")
    public String createReservation (@PathVariable Long storeId,
            @ModelAttribute CreateReservationForm form, Model model) {
        ReservationDto reservationDto = reservationApiClient.createReservation(form, storeId);
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
        List<ReservationDto> reservationList = reservationApiClient.getManagerReservations(managerId);
        model.addAttribute(reservationList);
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
        reservationApiClient.updateReservationStatus(reservationId, status);
        if (referer != null) {
            return "redirect:/" + referer;
        }
        return "manager/managerHome";
    }

    /**
     * 도착 확인 체크 하기
     */
    @PostMapping("/kiosk/check")
    public String updateArrivalStatus(@ModelAttribute ArrivalCheckForm form) {
        reservationApiClient.updateArrivalStatus(form);

        return "customer/customerHome"; //fixme
    }
}
