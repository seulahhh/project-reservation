package com.project.reservation.web;

import com.project.global.dto.CompleteReservationDto;
import com.project.global.dto.form.ArrivalCheckForm;
import com.project.global.dto.ReservationDto;
import com.project.global.dto.ReservationStatus;
import com.project.global.dto.form.CreateReservationForm;
import com.project.reservation.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReservationApiController {
    private final ReservationService reservationService;

    @Operation(
            summary = "customer가 새로운 예약을 신청하는 API",
            description = "customer가 매장에 새로운 예약을 시청하면 DB에 기본상태로 저장합니다"
    )
    @PostMapping("/reservation")
    public ResponseEntity<CompleteReservationDto> createReservation (
            @RequestBody CreateReservationForm form) {
        CompleteReservationDto reservationDto = reservationService.createReservation(form);
        log.info("managerId, from, Reservation Api Controller : {}", form.getManagerId());
        reservationService.notifyManagerOfRequest(form.getManagerId(), reservationDto);
        return ResponseEntity.ok(reservationDto);
    }

    @Operation(
            summary = "customer가 본인의 예약 일정을 조회하는 API",
            description = "customer가 본인의 이름으로 예약을 조회할 시 본인 아이디로 생성된 예약리스트를 보여줍니다. \n" +
                    "parmater로 필요한 customer의 Id는 현재 인증 정보를 통해 자동으로 불러옵니다."
    )
    @GetMapping("/customer/reservations/{customerId}")
    public ResponseEntity<List<ReservationDto>> getCustomerReservations (
            @PathVariable Long customerId) {
        return ResponseEntity.ok(reservationService.getCustomerReservations(customerId));
    }

    @Operation(
            summary = "manager가 본인의 매장의 예약 리스트를 조회하는 API",
            description = "manager가 본인 이름으로 등록한 매장의 예약 리스트를 불러옵니다."
    )
    @GetMapping("/manager/reservations/{storeId}")
    public ResponseEntity<List<ReservationDto>> getManagerReservations (
            @PathVariable Long storeId) {
        return ResponseEntity.ok(reservationService.getManagerReservations(storeId));
    }

    @Operation(
            summary = "customer가 보낸 예약신청에 대해 manager가 확정하는 API",
            description = "실시간 알람 혹은 예약 승인 대기중인 예약 리스트에 있는 예약의 상태를 '확정(CONFIRM)'상태로 " +
                    "변경합니다."
    )
    @PutMapping("/reservations/{reservationId}/status")
    public ResponseEntity<String> updateReservationStatus (
            @PathVariable Long reservationId,
            @RequestBody ReservationStatus status) {
        reservationService.updateReservationStatus(reservationId, status);
        return ResponseEntity.ok("ok");
    }

    @Operation(
            summary = "customer가 예약 10분 전까지 키오스크에 도착해 매장 도착 확인을 하는 API",
            description = "키오스크를 통해 매장 도착 확인을 하면 도착 상태가 변경됩니다.\n" +
                    "매장 도착 확인과 동시에 customer의 예약에 대한 유효성 검사도 진행합니다."
    )
    @PostMapping("/kiosk/reservations/arrival-check")
    public ResponseEntity<String> updateArrivalStatus (
            @RequestBody ArrivalCheckForm form) {
        log.info(form.toString());
        reservationService.updateArrivalStatus(form);
        return ResponseEntity.ok("ok");
    }

    /**
     * 특정 매장에 대한 고객의 예약 내역 조회
     */
    @GetMapping("/kiosk/store/{storeId}/reservations/{customerId}")
    public ResponseEntity<List<ReservationDto>> getCustomerStoreReservations (
            @PathVariable Long storeId,
            @PathVariable Long customerId) {
        List<ReservationDto> reservationDtoList = reservationService.getCustomerStoreReservations(customerId, storeId);
        return ResponseEntity.ok(reservationDtoList);
    }

}