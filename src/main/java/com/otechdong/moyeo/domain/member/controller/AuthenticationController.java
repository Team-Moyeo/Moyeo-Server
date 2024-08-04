package com.otechdong.moyeo.domain.member.controller;

import com.otechdong.moyeo.config.AuthenticationMember;
import com.otechdong.moyeo.domain.member.dto.MemberRequest;
import com.otechdong.moyeo.domain.member.dto.MemberResponse;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.member.service.MemberService;
import com.otechdong.moyeo.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class AuthenticationController {

    private final MemberService memberService;

    @PostMapping("/sign-in")
    public BaseResponse<MemberResponse.MemberSignIn> signIn(
            @RequestBody MemberRequest.MemberSignIn request) {
        return BaseResponse.onSuccess(memberService.signIn(request));
    }

    @GetMapping("/resign")
    public BaseResponse<MemberResponse.MemberResign> resign(
            @AuthenticationMember Member member) {
        return BaseResponse.onSuccess(memberService.resign(member));
    }
}
