package com.project.member.model.dto;

import com.project.member.domain.entity.Manager;
import com.project.member.domain.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ManagerDto {
    private String email;
    private String password;

    private String name;
    private String phone;

    private Store store;
}
