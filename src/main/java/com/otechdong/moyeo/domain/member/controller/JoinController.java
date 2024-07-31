package com.otechdong.moyeo.domain.member.controller;

import com.otechdong.moyeo.domain.member.dto.MemberRequest;
import com.otechdong.moyeo.domain.member.entity.SocialType;
import com.otechdong.moyeo.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class JoinController {

    @PostMapping("/sign-up")
    public BaseResponse<MemberRequest.MemberAppleSignIn> signUp(
            MemberRequest.MemberAppleSignIn request,
            @RequestParam SocialType socialType
    ) {
        return BaseResponse.onSuccess(null);
    }
}
