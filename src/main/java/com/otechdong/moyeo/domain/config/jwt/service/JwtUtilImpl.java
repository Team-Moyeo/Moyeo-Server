package com.otechdong.moyeo.domain.config.jwt.service;

import com.otechdong.moyeo.domain.config.jwt.dto.JwtProperties;
import com.otechdong.moyeo.domain.config.security.CustomUserDetails;
import com.otechdong.moyeo.domain.config.security.service.JpaUserDetailService;
import com.otechdong.moyeo.domain.member.dto.MemberResponse;
import com.otechdong.moyeo.domain.member.entity.PermissionRole;
import com.otechdong.moyeo.domain.member.mapper.AuthenticationMapper;
import com.otechdong.moyeo.global.exception.RestApiException;
import com.otechdong.moyeo.global.exception.errorCode.AuthErrorCode;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtilImpl implements JwtUtil {
    private SecretKey secretKey;
    private final JwtProperties jwtProperties;
    private final AuthenticationMapper authenticationMapper;
    private final JpaUserDetailService userDetailService;

    @PostConstruct
    protected void init() {
        secretKey = new SecretKeySpec(jwtProperties.getJwt_secret().getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS512.key().build().getAlgorithm());
    }


    //토큰 발급

    @Override
    public String createJwt(Long memberId, String clientId, String permissionRole, String tokenType) {
        Long expiredTime = 0L;
        if (tokenType == "refresh") {
            expiredTime = jwtProperties.getRefresh_expired_time();
        } else if (tokenType == "access") {
            expiredTime = jwtProperties.getAccess_expired_time();
        } else {
            throw new RestApiException(AuthErrorCode.INVALID_TOKEN_TYPE);
        }
        return Jwts.builder()
                .claim("memberId", memberId)
                .claim("tokenType", tokenType)
                .claim("clientId", clientId)
                .claim("permissionRole", permissionRole)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    @Override
    public String createRefreshToken(SecretKey secretKey) {
        Claims claims = (Claims) Jwts
                .claims();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 14))//유효시간 (3일)
                .signWith(SignatureAlgorithm.HS256, secretKey) //HS256알고리즘으로 key를 암호화 해줄것이다.
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
    public MemberResponse.MemberTokens refreshTokens(Long memberId, String clientId, PermissionRole permissionRole) {
        String accessToken = createJwt(memberId, clientId, permissionRole.getToKrean(), "access");
        String refreshToken = createRefreshToken(secretKey);
        return authenticationMapper.toMemberTokens(accessToken, refreshToken);
    }

    @Override
    public Authentication getAuthentication(String token) {
        CustomUserDetails userDetails = userDetailService.loadUserByUsername(getClientId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public Long getMemberId(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("memberId", Long.class);
    }

    @Override
    public String getClientId(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("clientId", String.class);
    }

    @Override
    public String getPermissionRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("permissionRole", String.class);
    }

    @Override
    public String getTokenType(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("tokenType", String.class);
    }

    @Override
    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

}
