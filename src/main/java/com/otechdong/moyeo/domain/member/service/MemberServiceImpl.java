package com.otechdong.moyeo.domain.member.service;

import com.otechdong.moyeo.domain.config.jwt.dto.TokenInfo;
import com.otechdong.moyeo.domain.config.jwt.service.JwtUtil;
import com.otechdong.moyeo.domain.member.dto.MemberRequest;
import com.otechdong.moyeo.domain.member.dto.MemberResponse;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.member.entity.SocialType;
import com.otechdong.moyeo.domain.member.mapper.MemberMapper;
import com.otechdong.moyeo.domain.member.mapper.AuthenticationMapper;
import com.otechdong.moyeo.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final JwtUtil jwtUtil;
    private final AuthenticationMapper authenticationMapper;

    @Override
    public MemberResponse.MemberSignIn signIn(MemberRequest.MemberSignIn request) {

        String clientId = request.getEncryptedUserIdentifier();
        SocialType socialType = request.getSocialType();

        Optional<Member> optionalMember = memberRepository.findByClientId(clientId);

        System.out.println(optionalMember);
        System.out.println(clientId);
        System.out.println(socialType);

        // 1. 해당 유저가 존재하지 않으면 : Member 객체 생성하고 DB에 저장 -> 회원가입
        if (optionalMember.isEmpty()) {
            // TODO: 새로운 유저를 만들고 디비에 저장 & JWT Token 생성
            return saveNewMember(clientId, socialType);
        }
        // 2. 해당 유저가 존재한다면 : Member 객체를 DB에서 불러오고, MemberSignIn response 반환
        // TODO: 토큰 유효 시간을 검사해서 accessToken 또는 refreshToken의 유효 기간이 만료되었을 때, 만료된 토큰을 각각 재발급해주는 로직 구현
        boolean isServiced = optionalMember.get().getName() != null;
        return generateNewToken(optionalMember.get(), isServiced);
    }

    private MemberResponse.MemberSignIn saveNewMember(String clientId, SocialType socialType) {
        Member member = memberMapper.toMember(clientId, socialType);
        Member newMember = memberRepository.save(member);

        return generateNewToken(newMember, false);
    }

    private MemberResponse.MemberSignIn generateNewToken(Member member, Boolean isServiced) {
        // TODO: Jwt 토큰 발급 구현
        Long memberId = member.getId();
        String clientId = member.getClientId();
        String permissionRole = member.getPermissionRole().getToKrean();

        String accessToken = jwtUtil.createJwt(memberId, clientId, permissionRole, "access");
        String refreshToken = jwtUtil.createJwt(memberId, clientId, permissionRole, "refresh");
        TokenInfo tokenInfo = authenticationMapper.toTokenInfo(accessToken, refreshToken);

        // log
        System.out.println(jwtUtil.getMemberId(accessToken));
        System.out.println(jwtUtil.getClientId(accessToken));
        System.out.println(jwtUtil.getTokenType(accessToken));
        System.out.println(jwtUtil.getPermissionRole(accessToken));

        return memberMapper.toMemberSignIn(member, tokenInfo, isServiced);
    }
}



