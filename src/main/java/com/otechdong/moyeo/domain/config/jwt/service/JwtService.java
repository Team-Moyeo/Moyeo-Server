package com.otechdong.moyeo.domain.config.jwt.service;

public interface JwtService {
    String createJwt(Long memberId, String role, String tokenType);
    String createJwtFromEncryptedUserIdentifier(String encryptedUserIdentifier);
}
