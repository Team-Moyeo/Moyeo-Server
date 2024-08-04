package com.otechdong.moyeo.domain.member.service;

import com.otechdong.moyeo.domain.member.dto.MemberRequest;
import com.otechdong.moyeo.domain.member.dto.MemberResponse;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.member.entity.SocialType;

public interface MemberService {
    MemberResponse.MemberSignIn signIn(MemberRequest.MemberSignIn request);

    MemberResponse.MemberResign resign(Member member);
}
