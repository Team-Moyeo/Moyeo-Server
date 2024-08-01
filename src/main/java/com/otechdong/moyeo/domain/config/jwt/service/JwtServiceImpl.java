package com.otechdong.moyeo.domain.config.jwt.service;

import com.otechdong.moyeo.domain.config.jwt.dto.JwtProperties;
import com.otechdong.moyeo.global.exception.RestApiException;
import com.otechdong.moyeo.global.exception.errorCode.AuthErrorCode;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private SecretKey secretKey;
    private JwtProperties jwtProperties;

    @PostConstruct
    protected void init() {
        secretKey = new SecretKeySpec(jwtProperties.getJwt_secret().getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS512.key().build().getAlgorithm());
    }


    @Override
    public String createJwt(Long memberId, String permissionRole, String tokenType) {
        Long expiredTime = 0L;
        if (tokenType == "refresh") {
            expiredTime = jwtProperties.getRefresh_expired_time();
        } else if (tokenType == "access") {
            expiredTime = jwtProperties.getAccess_expired_time();
        } else {
            throw new RestApiException(AuthErrorCode.INVALID_TOKEN_TYPE);
        }
        return Jwts.builder()
                .claim("tokenType", tokenType)
                .claim("memberId", memberId)
                .claim("permissionRole", permissionRole)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    @Override
    public String createJwtFromEncryptedUserIdentifier(String encryptedUserIdentifier) {
        SecretKey encryptedUserIdentifierSecretKey = new SecretKeySpec((jwtProperties.getJwt_secret()+encryptedUserIdentifier).getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS512.key().build().getAlgorithm());
        return Jwts.builder()
                .setAudience("capple")
                .signWith(SignatureAlgorithm.HS512, encryptedUserIdentifierSecretKey)
                .compact();
    }

}
