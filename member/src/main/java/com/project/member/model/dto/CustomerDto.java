package com.project.member.model.dto;

import com.project.member.domain.entity.Store;
import lombok.Data;
import lombok.Getter;

@Getter
public class CustomerDto {
    private String email;
    private String password;

    private String name;
    private String phone;

}
