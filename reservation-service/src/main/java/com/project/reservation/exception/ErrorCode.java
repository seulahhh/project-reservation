package com.project.reservation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    RESERVATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "예약 내역이 존재하지 않습니다"),
    INVALID_RESERVATION_STATUS(HttpStatus.BAD_REQUEST, "유효하지 않은 예약상태입니다"),
    RESERVATION_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 예약 내역이 존재합니다"),
    ARRIVAL_TIME_EXCEEDED(HttpStatus.BAD_REQUEST, "도착 확인 가능한 시간이 지났습니다."),
    ARRIVAL_ALREADY_CHECKED(HttpStatus.BAD_REQUEST, "이미 도착 확인이 완료되었습니다.");

    private final HttpStatus httpStatus;
    private final String description;
}
