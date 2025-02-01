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
    ARRIVAL_ALREADY_CHECKED(HttpStatus.BAD_REQUEST, "이미 도착 확인이 완료되었습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다"),
    LOAD_PAGE_FAIL(HttpStatus.NOT_FOUND, "페이지를 찾을 수 없습니다"),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "매장을 찾을 수 없습니다"),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다"),
    REVIEW_CANNOT_(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다"),
    REVIEW_DELETE_PERMISSION_DENIED(HttpStatus.BAD_REQUEST, "리뷰를 삭제할 수 있는 권한이 없습니다."),
    REVIEW_EDIT_PERMISSION_DENIED(HttpStatus.BAD_REQUEST, "리뷰를 작성한 사용자만 리뷰를 수정할 수 있습니다."),
    REVIEW_WRITE_PERMISSION_DENIED(HttpStatus.BAD_REQUEST, "예약이용 했던 매장에 한해 리뷰를 작성할 수 있습니다."),
    STORE_ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, "이미 등록하신 매장이 있습니다.")


            ;
    private final HttpStatus httpStatus;
    private final String description;
}
