package com.otechdong.moyeo.domain.member.mapper;

import com.otechdong.moyeo.config.jwt.dto.TokenInfo;
import com.otechdong.moyeo.domain.member.dto.MemberResponse;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.member.entity.PermissionRole;
import com.otechdong.moyeo.domain.member.entity.SocialType;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member toMember(final String clientId, SocialType socialType) {
        return Member.builder()
                .clientId(clientId)
                .name("")
                .socialType(socialType)
                .permissionRole(PermissionRole.ADMIN)
                .avatar("")
                .phoneNumber("")
                .email("")
                .build();
    }

    public MemberResponse.MemberSignIn toMemberSignIn(final Member member, TokenInfo tokenInfo, Boolean isServiced) {
        return MemberResponse.MemberSignIn.builder()
                .memberId(member.getId())
                .isServiced(isServiced)
                .accessToken(tokenInfo.getAccessToken())
                .refreshToken(tokenInfo.getRefreshToken())
                .build();
    }

    public MemberResponse.MemberResign toMemberResign(Member member) {
        return MemberResponse.MemberResign.builder()
                .memberId(member.getId())
                .build();
    }

    public MemberResponse.MemberGetMyProfile toMemberGetMyProfile(Member member) {
        return MemberResponse.MemberGetMyProfile.builder()
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .email(member.getEmail())
                .build();
    }

    public MemberResponse.MemberUpdateMyProfile toUpdateMyProfile(Member member) {
        return MemberResponse.MemberUpdateMyProfile.builder()
                .memberId(member.getId())
                .build();
    }
}
