package com.otechdong.moyeo.domain.member.dto;

import com.otechdong.moyeo.domain.member.entity.SocialType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberRequest {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberSignIn {
        private String encryptedUserIdentifier;
        private SocialType socialType;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberUpdateMyProfile {
        private String name;
        private String phoneNumber;
        private String email;
    }
}
