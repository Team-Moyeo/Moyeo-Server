package com.otechdong.moyeo.domain.member.controller;

import com.otechdong.moyeo.config.AuthenticationMember;
import com.otechdong.moyeo.domain.member.dto.MemberRequest;
import com.otechdong.moyeo.domain.member.dto.MemberResponse;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.member.service.MemberService;
import com.otechdong.moyeo.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Tag(name = "(로그인) 멤버 API", description = "로그인 관련 멤버 API 입니다.")
public class AuthenticationController {

    private final MemberService memberService;


    @Operation(summary = "로그인 API", description = "로그인 API 입니다.")
    @PostMapping("/sign-in")
    public BaseResponse<MemberResponse.MemberSignIn> signIn(
            @RequestBody MemberRequest.MemberSignIn request) {
        return BaseResponse.onSuccess(memberService.signIn(request));
    }

    @Operation(summary = "회원 탈퇴 API", description = "회원 탈퇴 API 입니다.")
    @GetMapping("/resign")
    public BaseResponse<MemberResponse.MemberResign> resign(
            @AuthenticationMember Member member) {
        return BaseResponse.onSuccess(memberService.resign(member));
    }
}
