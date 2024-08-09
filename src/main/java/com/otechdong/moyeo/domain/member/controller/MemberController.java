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
@Tag(name = "멤버 API", description = "멤버 API 입니다.")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "마이 프로필 조회 API", description = "마이 프로필 조회 API 입니다.")
    @GetMapping("/profile")
    public BaseResponse<MemberResponse.MemberGetMyProfile> getMyProfile(
            @AuthenticationMember Member member) {
        return BaseResponse.onSuccess(memberService.getMyProfile(member));
    }

    @Operation(summary = "마이 프로필 업데이트 API", description = "마이 프로필 조회 API 입니다.")
    @GetMapping("/profile/update")
    public BaseResponse<MemberResponse.MemberUpdateMyProfile> updateMyProfile(
            @AuthenticationMember Member member,
            @RequestBody MemberRequest.MemberUpdateMyProfile request) {
        return BaseResponse.onSuccess(memberService.updateMyProfile(member, request));
    }

    @Operation(summary = "모임 참여자 조회 API", description = "모임 참여자 조회 API 입니다.")
    @GetMapping("/meeting/{meetingId}")
    public BaseResponse<MemberResponse.MemberGetListByMeeting> getMembersByMeeting(
            @PathVariable(name = "meetingId") Long meetingId
    ) {
        return BaseResponse.onSuccess(memberService.getMembersByMeeting(meetingId));
    }
}
