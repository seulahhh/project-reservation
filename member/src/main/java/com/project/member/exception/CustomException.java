package com.project.member.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;
    private final int status;
    private static final ObjectMapper mapper = new ObjectMapper();

    // todo mapper 사용 안하면 제거

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.status = errorCode.getHttpStatus().value();
    }
}
