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
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/profile")
    public BaseResponse<MemberResponse.MemberGetMyProfile> getMyProfile(
            @AuthenticationMember Member member) {
        return BaseResponse.onSuccess(memberService.getMyProfile(member));
    }

    @GetMapping("/profile/update")
    public BaseResponse<MemberResponse.MemberUpdateMyProfile> updateMyProfile(
            @AuthenticationMember Member member,
            @RequestBody MemberRequest.MemberUpdateMyProfile request) {
        return BaseResponse.onSuccess(memberService.updateMyProfile(member, request));
    }

    @GetMapping("/meeting/{meetingId}")
    public BaseResponse<MemberResponse.MemberGetListByMeeting> getMembersByMeeting(
            @PathVariable(name = "meetingId") Long meetingId
    ) {
        return BaseResponse.onSuccess(memberService.getMembersByMeeting(meetingId));
    }
}
