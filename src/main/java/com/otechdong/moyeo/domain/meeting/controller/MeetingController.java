package com.otechdong.moyeo.domain.meeting.controller;

import com.otechdong.moyeo.config.AuthenticationMember;
import com.otechdong.moyeo.domain.meeting.dto.MeetingRequest;
import com.otechdong.moyeo.domain.meeting.dto.MeetingResponse;
import com.otechdong.moyeo.domain.meeting.entity.MeetingStatus;
import com.otechdong.moyeo.domain.meeting.service.MeetingService;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meetings")
@Tag(name = "모임 API", description = "모임 API 입니다.")
public class MeetingController {

    private final MeetingService meetingService;

    @Operation(summary = "모임 생성 API", description = "모임 생성 API 입니다.")
    @PostMapping
    public BaseResponse<MeetingResponse.MeetingCreate> createMeeting(
            @AuthenticationMember Member member,
            @RequestBody MeetingRequest.MeetingCreate request) {
        return BaseResponse.onSuccess(meetingService.createMeeting(
                member,
                request.getTitle(),
                request.getStartDate(),
                request.getStartTime(),
                request.getEndDate(),
                request.getEndTime(),
                request.getFixedTimes(),
                request.getCandidatePlaces(),
                request.getFixedPlace(),
                request.getDeadline()));
    }

    @Operation(summary = "(상태별) 모임 조회 API", description = "(PENDING, CONFIRM, END) 모임 조회 API 입니다.")
    @GetMapping
    public BaseResponse<MeetingResponse.MeetingGetList> getMeetingsByMeetingStatus(
            @AuthenticationMember Member member,
            @RequestParam(name = "meetingStatus", required = false) MeetingStatus meetingStatus
    ) {
        return BaseResponse.onSuccess(meetingService.getMeetingsByMeetingStatus(member, meetingStatus));
    }

    @Operation(summary = "모임 삭제 API", description = "모임 삭제 API 입니다.")
    @DeleteMapping("/{meetingId}")
    public BaseResponse<MeetingResponse.MeetingDelete> deleteMeeting(
            @AuthenticationMember Member member,
            @PathVariable(value = "meetingId") Long meetingId
    ) {
        return BaseResponse.onSuccess(meetingService.deleteMeeting(member, meetingId));
    }

    @Operation(summary = "모임 가입 API", description = "초대코드를 통한 모임 가입 API 입니다.")
    @PostMapping("/join/{inviteCode}")
    public BaseResponse<MeetingResponse.MeetingJoinWithInviteCode> joinMeetingWithInviteCode(
            @AuthenticationMember Member member,
            @PathVariable(value = "inviteCode") String inviteCode
    ) {
        return BaseResponse.onSuccess(meetingService.joinMeetingWithInviteCode(member, inviteCode));
    }

    @Operation(summary = "초대코드 조회 API", description = "모임 초대코드 조회 API 입니다.")
    @GetMapping("/invite-code/{meetingId}")
    public BaseResponse<MeetingResponse.MeetingGetInviteCode> getInviteCode(
            @PathVariable(value = "meetingId") Long meetingId
    ) {
        return BaseResponse.onSuccess(meetingService.getInviteCode(meetingId));
    }

    @Operation(summary = "모임 상세 조회 API", description = "모임 상세 조회 API 입니다.")
    @GetMapping("/{meetingId}")
    public BaseResponse<MeetingResponse.MeetingGetDetail> getMeetingDetail(
            @AuthenticationMember Member member,
            @PathVariable(value = "meetingId") Long meetingId
    ) {
        return BaseResponse.onSuccess(meetingService.getMeetingDetail(member, meetingId));
    }

    @Operation(summary = "투표 확정 API", description = "투표 확정 API 입니다.")
    @PostMapping("/{meetingId}/vote")
    public BaseResponse<MeetingResponse.MeetingVoteConfirm> voteConfirm(
            @AuthenticationMember Member member,
            @PathVariable(value = "meetingId") Long meetingId,
            @RequestBody MeetingRequest.MeetingVoteConfirm request
    ) {
        return BaseResponse.onSuccess(meetingService.voteConfirm(member, meetingId, request.getCandidateTimeIds(), request.getCandidatePlaceIds()));
    }

    @Operation(summary = "재투표 API", description = "재투표 API 입니다.")
    @PostMapping("/{meetingId}/vote/update")
    public BaseResponse<MeetingResponse.MeetingVoteUpdate> voteUpdate(
            @AuthenticationMember Member member,
            @PathVariable(value = "meetingId") Long meetingId,
            @RequestBody MeetingRequest.MeetingVoteUpdate request
    ) {
        return BaseResponse.onSuccess(meetingService.voteUpdate(member, meetingId, request.getCandidateTimeIds(), request.getCandidatePlaceIds()));
    }
}
