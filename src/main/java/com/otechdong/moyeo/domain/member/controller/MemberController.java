package com.otechdong.moyeo.domain.member.controller;

import com.otechdong.moyeo.config.AuthenticationMember;
import com.otechdong.moyeo.domain.member.dto.MemberResponse;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.member.service.MemberService;
import com.otechdong.moyeo.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/profile")
    public BaseResponse<MemberResponse.MemberGetMyProfile> getMyProfile(
            @AuthenticationMember Member member) {
        return BaseResponse.onSuccess(memberService.getMyProfile(member));
    }
}
