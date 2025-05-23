package com.project.reservation.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionAdvice {

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<CustomException.CustomExceptionResponse> handleException (
            final CustomException exception) {
        return ResponseEntity.status(exception.getStatus())
                             .body(CustomException.CustomExceptionResponse.builder()
                                           .status(exception.getStatus())
                                           .code(exception.getErrorCode().name())
                                           .message(exception.getMessage())
                                           .build());
    }
}
