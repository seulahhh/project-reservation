package com.project.reservation.web;

import com.project.global.dto.ReservationDto;
import com.project.reservation.alarm.SseEmitterService;
import com.project.global.dto.form.CreateReservationForm;
import com.project.global.dto.ArrivalCheckForm;
import com.project.global.dto.ReservationStatus;
import com.project.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReservationApiController {
    private final SseEmitterService sseEmitterService;
    private final ReservationService reservationService;
    /**
     * 예약 생성하기
     * @param form
     * @return reservation Id
     */
    @PostMapping("/reservation")
    public ResponseEntity<ReservationDto> createReservation (@RequestBody CreateReservationForm form) {
        ReservationDto reservationDto = reservationService.createReservation(form);
        boolean ok = sseEmitterService.sendMessages(form.getManagerId(), reservationDto);

        return ResponseEntity.ok(reservationDto);
    }

    /**
     * 요청받은 예약정보 가져오기
     * @param customerId
     * @return ReservationDto
     */
    @GetMapping("/customer/reservation/{customerId}")
    public ResponseEntity<List<ReservationDto>> getReservation(@PathVariable Long customerId) {
        return ResponseEntity.ok(reservationService.getCustomerReservations(customerId));
    }

    /**
     * manager가 예약 확인 후 예약 승인 상태 변경
     * @param reservationId
     * @return String status
     */
    @PutMapping("/manager/reservation/{reservationId}")
    public ResponseEntity<String> updateReservationStatus(@PathVariable Long reservationId,
            @RequestBody ReservationStatus status) {
        reservationService.updateReservationStatus(reservationId, status);
        return ResponseEntity.ok("ok");
    }

    /**
     * 고객이 예약 확인 후 도착 상태 변경
     * @param form 키오스크에서 보내는 ArrivalCheckForm
     * @return String status
     */
    @PostMapping("/kiosk/check")
    public ResponseEntity<String> updateArrivalStatus(@RequestBody ArrivalCheckForm form) {
        reservationService.updateArrivalStatus(form);
        return ResponseEntity.ok("ok");
    }

    // 예약 취소하기  todo
    // 예약 정보 수정하기 todo
}