package com.project.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다"),
    LOAD_PAGE_FAIL(HttpStatus.NOT_FOUND, "페이지를 찾을 수 없습니다"),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "매장을 찾을 수 없습니다"),
    USER_HAS_EXISTING_STORE(HttpStatus.BAD_REQUEST, "사용자가 이미 등록한 매장이 있습니다")
    ;

    private final HttpStatus httpStatus;
    private final String description;
}