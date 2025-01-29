package com.project.reservation.web;

import com.project.reservation.model.dto.CreateReservationForm;
import com.project.reservation.model.dto.ReservationDto;
import com.project.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReservationApiController {
    private final ReservationService reservationService;
//    /**
//     * 예약 생성하기
//     * - Customer
//     */
//    @PostMapping("/customer/stores/{storeId}/reservation")
//    public String createReservation(CreateReservationForm form) {
//        reservationService.createReservation(form);
//        // 내 예약 내역 확인 페이지로 이동
//        return "";
//    }

    /**
     * 예약 생성하기
     * @param form
     * @return reservation Id
     */
    @PostMapping("/api/reservation/new")
    public ResponseEntity<ReservationDto> testController(@RequestBody CreateReservationForm form) {
        System.out.println(form);
        ReservationDto reservationDto = reservationService.createReservation(form);
        // reservation Id
        return ResponseEntity.ok(reservationDto);
    }

    /**
     * 예약 확인하기
     */


}