package com.project.member.model.dto.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MessageDto {
    private String message;
    private String status;
    private String redirectUrl;
}
