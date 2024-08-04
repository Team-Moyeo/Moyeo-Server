package com.otechdong.moyeo.domain.meeting.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MeetingStatus {
    PENDING("투표 진행중"),
    CONFIRM("투표 확정"),
    END("모임 진행");
    private final String toKorean;
}