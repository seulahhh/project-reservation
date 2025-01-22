package com.project.member.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    MANAGER("manager"), CUSTOMER("customer");
    private final String name;
}
