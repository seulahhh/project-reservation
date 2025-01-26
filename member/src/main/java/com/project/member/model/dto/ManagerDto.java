package com.project.member.model.dto;

import com.project.member.persistence.entity.Store;
import lombok.Getter;

@Getter
public class ManagerDto {
    private String email;
    private String password;

    private String name;
    private String phone;

    private Store store;
}
