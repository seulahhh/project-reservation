package com.project.member.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 추후 global 로 분리 예정
 */
@ControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler({CustomException.class})
    public String handleException (
            final CustomException exception,
            Model model) {
        model.addAttribute("alertCode", exception.getErrorCode());
        model.addAttribute("alertMessage", exception.getMessage());
        return "error-page";
    }
}
