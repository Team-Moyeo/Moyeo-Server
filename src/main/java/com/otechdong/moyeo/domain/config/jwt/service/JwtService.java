package com.otechdong.moyeo.domain.config.jwt.service;

import com.otechdong.moyeo.domain.member.dto.MemberResponse;
import com.otechdong.moyeo.domain.member.entity.PermissionRole;

public interface JwtService {
    String createJwt(Long memberId, String role, String tokenType);

//    String createJwtFromEncryptedUserIdentifier(String encryptedUserIdentifier);

    Boolean checkJwt(String token);

    MemberResponse.MemberTokens refreshTokens(Long memberId, PermissionRole permissionRole);
}
