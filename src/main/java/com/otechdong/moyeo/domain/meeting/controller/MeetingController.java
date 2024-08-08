package com.otechdong.moyeo.domain.meeting.controller;

import com.otechdong.moyeo.config.AuthenticationMember;
import com.otechdong.moyeo.domain.meeting.dto.MeetingRequest;
import com.otechdong.moyeo.domain.meeting.dto.MeetingResponse;
import com.otechdong.moyeo.domain.meeting.entity.MeetingStatus;
import com.otechdong.moyeo.domain.meeting.service.MeetingService;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meetings")
public class MeetingController {

    private final MeetingService meetingService;

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

    @GetMapping
    public BaseResponse<MeetingResponse.MeetingGetList> getMeetingsByMeetingStatus(
            @AuthenticationMember Member member,
            @RequestParam(name = "meetingStatus", required = false) MeetingStatus meetingStatus
    ) {
        return BaseResponse.onSuccess(meetingService.getMeetingsByMeetingStatus(member, meetingStatus));
    }

    @DeleteMapping("/{meetingId}")
    public BaseResponse<MeetingResponse.MeetingDelete> deleteMeeting(
            @AuthenticationMember Member member,
            @PathVariable(value = "meetingId") Long meetingId
    ) {
        return BaseResponse.onSuccess(meetingService.deleteMeeting(member, meetingId));
    }

    @PostMapping("/join/{inviteCode}")
    public BaseResponse<MeetingResponse.MeetingJoinWithInviteCode> joinMeetingWithInviteCode(
            @AuthenticationMember Member member,
            @PathVariable(value = "inviteCode") String inviteCode
    ) {
        return BaseResponse.onSuccess(meetingService.joinMeetingWithInviteCode(member, inviteCode));
    }

    @GetMapping("/invite-code/{meetingId}")
    public BaseResponse<MeetingResponse.MeetingGetInviteCode> getInviteCode(
            @PathVariable(value = "meetingId") Long meetingId
    ) {
        return BaseResponse.onSuccess(meetingService.getInviteCode(meetingId));
    }

    @GetMapping("/{meetingId}")
    public BaseResponse<MeetingResponse.MeetingGetDetail> getMeetingDetail(
            @AuthenticationMember Member member,
            @PathVariable(value = "meetingId") Long meetingId
    ) {
        return BaseResponse.onSuccess(meetingService.getMeetingDetail(member, meetingId));
    }

    @PostMapping("/{meetingId}/vote")
    public BaseResponse<MeetingResponse.MeetingVoteConfirm> voteConfirm(
            @AuthenticationMember Member member,
            @PathVariable(value = "meetingId") Long meetingId,
            @RequestBody MeetingRequest.MeetingVoteConfirm request
    ) {
        return BaseResponse.onSuccess(meetingService.voteConfirm(member, meetingId, request.getCandidateTimeIds(), request.getCandidatePlaceIds()));
    }
}
