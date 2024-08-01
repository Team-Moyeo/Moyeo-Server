package com.otechdong.moyeo.domain.member.service;

import com.otechdong.moyeo.domain.member.dto.MemberRequest;
import com.otechdong.moyeo.domain.member.dto.MemberResponse;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.member.entity.SocialType;
import com.otechdong.moyeo.domain.member.mapper.MemberMapper;
import com.otechdong.moyeo.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    public MemberResponse.MemberSignIn signIn(
            MemberRequest.MemberAppleSignIn request,
            SocialType socialType) {

        String encryptedUserIdentifier = request.getEncryptedUserIdentifier();

        Optional<Member> optionalMember = memberRepository.findByClientId(request.getEncryptedUserIdentifier());

        // 1. 해당 유저가 존재하지 않으면 : Member 객체 생성하고 DB에 저장 -> 회원가입
        if (optionalMember.isEmpty()) {
            // TODO: 새로운 유저를 만들고 디비에 저장 & JWT Token 생성

            return null;
        }
        // 2. 해당 유저가 존재한다면 : Member 객체를 DB에서 불러오고, MemberSignIn response 반환
        return null;
    }

//    private MemberResponse.MemberSignIn saveNewMember(String clientId, SocialType socialType) {
//        Member member = memberMapper.toMember(clientId, socialType);
//        Member newMember = memberRepository.save(member);
//
//        return generateNewToken(newMember, false);
//    }
//
//    private MemberResponse.MemberSignIn generateNewToken(Member member, Boolean isServiced) {
//        // TODO: Jwt 토큰 발급 구현
////    TokenInfo tokenInfo =
//
//        return memberMapper.toMemberSignIn(member, TokenInfo, isServiced);
//    }
}



