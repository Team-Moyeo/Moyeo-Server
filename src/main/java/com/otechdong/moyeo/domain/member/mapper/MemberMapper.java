package com.otechdong.moyeo.domain.member.mapper;

import com.otechdong.moyeo.domain.config.jwt.dto.TokenInfo;
import com.otechdong.moyeo.domain.member.dto.MemberResponse;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.member.entity.PermissionRole;
import com.otechdong.moyeo.domain.member.entity.Role;
import com.otechdong.moyeo.domain.member.entity.SocialType;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member toMember(final String clientId, SocialType socialType) {
        return Member.builder()
                .clientId(clientId)
                .name("이경수")
                .socialType(socialType)
                .role(Role.OWNER)
                .permissionRole(PermissionRole.ADMIN)
                .avatar("https://nasadf")
                .phoneNumber("010-9196-7601")
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

}
