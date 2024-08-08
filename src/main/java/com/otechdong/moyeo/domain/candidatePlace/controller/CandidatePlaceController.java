package com.otechdong.moyeo.domain.candidatePlace.controller;

import com.otechdong.moyeo.config.AuthenticationMember;
import com.otechdong.moyeo.domain.candidatePlace.service.CandidatePlaceService;
import com.otechdong.moyeo.domain.meeting.dto.MeetingResponse;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/candidate-place")
public class CandidatePlaceController {

    private final CandidatePlaceService candidatePlaceService;

    @PostMapping("/{meetingId}/{placeId}")
    public BaseResponse<MeetingResponse.MeetingAddCandidatePlace> addCandidatePlace(
            @AuthenticationMember Member member,
            @PathVariable(value = "meetingId") Long meetingId,
            @PathVariable(value = "placeId") Long placeId
    ) {
        return BaseResponse.onSuccess(candidatePlaceService.addCandidatePlace(member, meetingId, placeId));
    }

    @DeleteMapping("/{meetingId}/{candidatePlaceId}")
    public BaseResponse<MeetingResponse.MeetingDeleteCandidatePlace> deleteCandidatePlace(
            @AuthenticationMember Member member,
            @PathVariable(value = "meetingId") Long meetingId,
            @PathVariable(value = "candidatePlaceId") Long candidatePlaceId
    ) {
        return BaseResponse.onSuccess(candidatePlaceService.deleteCandidatePlace(member, meetingId, candidatePlaceId));
    }
}
