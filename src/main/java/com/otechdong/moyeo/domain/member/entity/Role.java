package com.otechdong.moyeo.domain.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    OWNER("주최자"),
    PARTICIPANTS("참여자");
    private final String toKorean;
}
