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

    @PostMapping("/{meetingId}/candidate-place/{placeId}")
    public BaseResponse<MeetingResponse.MeetingAddCandidatePlace> addCandidatePlace(
            @AuthenticationMember Member member,
            @PathVariable(value = "meetingId") Long meetingId,
            @PathVariable(value = "placeId") Long placeId
    ) {
        return BaseResponse.onSuccess(meetingService.addCandidatePlace(member, meetingId, placeId));
    }

    @GetMapping
    public BaseResponse<MeetingResponse.MeetingGetList> getMeetingsByMeetingStatus(
            @AuthenticationMember Member member,
            @RequestParam(name = "meetingStatus", required = false) MeetingStatus meetingStatus
    ) {
        System.out.println("TEST");
        return BaseResponse.onSuccess(meetingService.getMeetingsByMeetingStatus(member, meetingStatus));
    }

    @DeleteMapping("/{meetingId}")
    public BaseResponse<MeetingResponse.MeetingDelete> deleteMeeting(
            @AuthenticationMember Member member,
            @PathVariable(value = "meetingId") Long meetingId
    ) {
        return BaseResponse.onSuccess(meetingService.deleteMeeting(member, meetingId));
    }

}
