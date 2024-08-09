package com.otechdong.moyeo.domain.candidatePlace.controller;

import com.otechdong.moyeo.config.AuthenticationMember;
import com.otechdong.moyeo.domain.candidatePlace.service.CandidatePlaceService;
import com.otechdong.moyeo.domain.meeting.dto.MeetingResponse;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/candidate-place")
@Tag(name = "후보 장소 API", description = "후보 장소 API 입니다.")
public class CandidatePlaceController {

    private final CandidatePlaceService candidatePlaceService;

    @Operation(summary = "후보 장소 등록 API", description = "후보 장소 등록 API 입니다.")
    @PostMapping("/{meetingId}/{placeId}")
    public BaseResponse<MeetingResponse.MeetingAddCandidatePlace> addCandidatePlace(
            @AuthenticationMember Member member,
            @PathVariable(value = "meetingId") Long meetingId,
            @PathVariable(value = "placeId") Long placeId
    ) {
        return BaseResponse.onSuccess(candidatePlaceService.addCandidatePlace(member, meetingId, placeId));
    }

    @Operation(summary = "후보 장소 삭제 API", description = "후보 장소 삭제 API 입니다.")
    @DeleteMapping("/{meetingId}/{candidatePlaceId}")
    public BaseResponse<MeetingResponse.MeetingDeleteCandidatePlace> deleteCandidatePlace(
            @AuthenticationMember Member member,
            @PathVariable(value = "meetingId") Long meetingId,
            @PathVariable(value = "candidatePlaceId") Long candidatePlaceId
    ) {
        return BaseResponse.onSuccess(candidatePlaceService.deleteCandidatePlace(member, meetingId, candidatePlaceId));
    }
}
