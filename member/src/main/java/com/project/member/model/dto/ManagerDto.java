package com.project.member.model.dto;

import lombok.Getter;

@Getter
public class ManagerDto {
    private String email;
    private String password;

    private String name;
    private String phone;

    private Long storeId;
}
