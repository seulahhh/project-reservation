package com.project.reservation.model.types;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ReservationStatus {
    PENDING_APPROVAL("매니저가 예약 승인 대기중"),
    CONFIRMED("매니저의 예약 승인 완료"),
    IN_PROGRESS("예약된 식사가 진행중"),
    CANCELED_BY_NO_SHOW("고객이 도착 확인을 하지 않아 취소됨"),
    CANCELED("예약 취소됨"), // 다른 이유등
    FINISHED("정상적으로 예약된 식사가 끝이 남");

    private final String description;
}
