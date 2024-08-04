package com.otechdong.moyeo.domain.member.service;

import com.otechdong.moyeo.domain.member.dto.MemberRequest;
import com.otechdong.moyeo.domain.member.dto.MemberResponse;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.member.entity.SocialType;

public interface MemberService {

    Member findById(Long memberId);
    MemberResponse.MemberSignIn signIn(MemberRequest.MemberSignIn request);

    MemberResponse.MemberResign resign(Member member);

    MemberResponse.MemberGetMyProfile getMyProfile(Member member);

    MemberResponse.MemberUpdateMyProfile updateMyProfile(Member member, MemberRequest.MemberUpdateMyProfile request);
}
