package com.otechdong.moyeo.domain.config.jwt.service;

import com.otechdong.moyeo.domain.config.jwt.dto.JwtProperties;
import com.otechdong.moyeo.domain.member.dto.MemberResponse;
import com.otechdong.moyeo.domain.member.entity.PermissionRole;
import com.otechdong.moyeo.domain.member.mapper.TokenMapper;
import com.otechdong.moyeo.global.exception.RestApiException;
import com.otechdong.moyeo.global.exception.errorCode.AuthErrorCode;
import io.jsonwebtoken.*;
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
    private final JwtProperties jwtProperties;
    private final TokenMapper tokenMapper;

    @PostConstruct
    protected void init() {
        secretKey = new SecretKeySpec(jwtProperties.getJwt_secret().getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS512.key().build().getAlgorithm());
    }


    //토큰 발급

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

//    @Override
//    public String createJwtFromEncryptedUserIdentifier(String encryptedUserIdentifier) {
//        SecretKey encryptedUserIdentifierSecretKey = new SecretKeySpec((jwtProperties.getJwt_secret()+encryptedUserIdentifier).getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS512.key().build().getAlgorithm());
//        return Jwts.builder()
//                .setAudience("capple")
//                .signWith(SignatureAlgorithm.HS512, encryptedUserIdentifierSecretKey)
//                .compact();
//    }

    // 토큰 유효성 검사
    @Override
    public Boolean checkJwt(String token) { //TODO 토큰 유효성 검증 로직 수정 필요
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            throw new RestApiException(AuthErrorCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new RestApiException(AuthErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new RestApiException(AuthErrorCode.UNSUPPORTED_TOKEN);
        } catch (SignatureException e) {
            throw new RestApiException(AuthErrorCode.WRONG_TOKEN_SIGNITURE);
        } catch (IllegalArgumentException e) {
            throw new RestApiException(AuthErrorCode.EMPTY_TOKEN);
        }
    }

    // 토큰 재발급
    @Override
    public MemberResponse.MemberTokens refreshTokens(Long memberId, PermissionRole permissionRole) {
        String accessToken = createJwt(memberId, permissionRole.getToKrean(), "access");
        String refreshToken = createJwt(memberId, permissionRole.getToKrean(), "refresh");
        return tokenMapper.toTokens(accessToken, refreshToken);
    }

}
