package com.project.member.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Message {
    DELETE_COMPLETE("삭제 완료하였습니다"),
    ADD_COMPLETE("등록 완료하였습니다"),
    UPDATE_COMPLETE("수정 완료하였습니다")
    ;
    private final String body;
}
