package com.otechdong.moyeo.domain.member.mapper;

import com.otechdong.moyeo.domain.member.dto.MemberResponse;
import org.springframework.stereotype.Component;

@Component
public class TokenMapper {
    public MemberResponse.MemberTokens toTokens(String accessToken, String refreshToken) {
        return MemberResponse.MemberTokens.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
