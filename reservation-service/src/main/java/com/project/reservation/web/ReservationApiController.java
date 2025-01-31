package com.project.reservation.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.global.dto.ArrivalCheckForm;
import com.project.global.dto.ReservationDto;
import com.project.global.dto.ReservationStatus;
import com.project.global.dto.form.CreateReservationForm;
import com.project.infrastructure.kafka.KafkaProducer;
import com.project.reservation.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReservationApiController {
    private final ReservationService reservationService;
    private final KafkaProducer kafkaProducer;

    @Operation(
            summary = "customer가 새로운 예약을 신청하는 API",
            description = "customer가 매장에 새로운 예약을 시청하면 DB에 기본상태로 저장합니다"
    )
    @PostMapping("/reservation")
    public ResponseEntity<ReservationDto> createReservation (
            @RequestBody CreateReservationForm form) {

        ReservationDto reservationDto =
                reservationService.createReservation(form);

//        boolean ok = sseEmitterService.sendMessages(form.getManagerId(),
//                                                    reservationDto);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            String msg = objectMapper.writeValueAsString(reservationDto);
            kafkaProducer.sendMessage("toManager", msg, form.getManagerId());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

        return ResponseEntity.ok(reservationDto);
    }

    @Operation(
            summary = "customer가 본인의 예약 일정을 조회하는 API",
            description = "customer가 본인의 이름으로 예약을 조회할 시 본인 아이디로 생성된 예약리스트를 보여줍니다. \n" +
                    "parmater로 필요한 customer의 Id는 현재 인증 정보를 통해 자동으로 불러옵니다."
    )
    @GetMapping("/customer/reservation/{customerId}")
    public ResponseEntity<List<ReservationDto>> getCustomerReservations (
            @PathVariable Long customerId) {
        return ResponseEntity.ok(reservationService.getCustomerReservations(customerId));
    }

    @Operation(
            summary = "customer가 보낸 예약신청에 대해 manager가 확정하는 API",
            description = "실시간 알람 혹은 예약 승인 대기중인 예약 리스트에 있는 예약의 상태를 '확정(CONFIRM)'상태로 " +
                    "변경합니다."
    )
    @PutMapping("/manager/reservation/{reservationId}")
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
    @PostMapping("/kiosk/check")
    public ResponseEntity<String> updateArrivalStatus (
            @RequestBody ArrivalCheckForm form) {
        reservationService.updateArrivalStatus(form);
        return ResponseEntity.ok("ok");
    }

    @Operation(
            summary = "manager가 본인의 매장의 예약 리스트를 조회하는 API",
            description = "manager가 본인 이름으로 등록한 매장의 예약 리스트를 불러옵니다."
    )
    @GetMapping("/manager/reservation/{storeId}")
    public ResponseEntity<List<ReservationDto>> getManagerReservations (
            @PathVariable Long storeId) {
        return ResponseEntity.ok(reservationService.getManagerReservations(storeId));
    }


    // 예약 취소하기  todo
    // 예약 정보 수정하기 todo
}