package com.project.global.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ReservationStatus {
    PENDING_APPROVAL("승인대기"),
    CONFIRMED("예약확정"),
    IN_PROGRESS("예약된 식사가 진행중"),
    CANCELED_BY_NO_SHOW("고객이 도착 확인을 하지 않아 취소됨"),
    CANCELED("예약취소"), // 다른 이유등
    FINISHED("정상적으로 예약된 식사가 끝이 남");
    public String getMessage() {
        return this.description;
    }
    private final String description;
}
