package com.otechdong.moyeo.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class MemberResponse {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberSignIn {
        private Long memberId;
        private String accessToken;
        private String refreshToken;
        private Boolean isServiced;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberTokens {
        private String accessToken;
        private String refreshToken;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberResign {
        private Long memberId;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberGetMyProfile {
        private String name;
        private String phoneNumber;
        private String email;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberUpdateMyProfile {
        private Long memberId;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberGetListByMeeting {
        private List<MemberGetListByMeetingMemberInfo> memberInfos;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberGetListByMeetingMemberInfo {
        private String name;
        private String avatar;
    }
}
