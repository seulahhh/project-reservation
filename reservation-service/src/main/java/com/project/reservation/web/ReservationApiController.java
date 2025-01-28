package com.project.reservation.web;

import com.project.reservation.model.dto.CreateReservationForm;
import com.project.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationApiController {
    private final ReservationService reservationService;
    /**
     * 예약 생성하기
     * - Customer
     */
    @PostMapping("/customer/stores/{storeId}/reservation")
    public String createReservation(CreateReservationForm form) {
        reservationService.createReservation(form);
        // 내 예약 내역 확인 페이지로 이동
        return "";
    }

    @GetMapping("/reservation")
    public ResponseEntity<String> testController() {
        System.out.println("test성공");
        return ResponseEntity.ok("test성공");
    }
}