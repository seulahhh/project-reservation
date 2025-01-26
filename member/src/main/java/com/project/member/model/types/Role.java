package com.project.member.model.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 아직 사용 X
 */
@Getter
@RequiredArgsConstructor
public enum Role {
    MANAGER("manager"), CUSTOMER("customer");
    private final String name;
}
