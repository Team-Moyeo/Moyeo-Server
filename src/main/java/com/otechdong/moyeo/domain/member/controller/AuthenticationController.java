package com.otechdong.moyeo.domain.member.controller;

import com.otechdong.moyeo.domain.member.dto.MemberRequest;
import com.otechdong.moyeo.domain.member.dto.MemberResponse;
import com.otechdong.moyeo.domain.member.service.MemberService;
import com.otechdong.moyeo.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class AuthenticationController {

    private final MemberService memberService;

    @PostMapping("/sign-in")
    public BaseResponse<MemberResponse.MemberSignIn> signIn(
            @RequestBody MemberRequest.MemberSignIn request
    ) {
        return BaseResponse.onSuccess(memberService.signIn(request));
    }
}
