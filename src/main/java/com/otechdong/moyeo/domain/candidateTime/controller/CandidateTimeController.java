package com.otechdong.moyeo.domain.candidateTime.controller;

import com.otechdong.moyeo.config.AuthenticationMember;
import com.otechdong.moyeo.domain.candidateTime.dto.CandidateTimeResponse;
import com.otechdong.moyeo.domain.candidateTime.service.CandidateTimeService;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/candidate-times")
@Tag(name = "후보 시간 API", description = "후보 시간 API 입니다.")
public class CandidateTimeController {

    private final CandidateTimeService candidateTimeService;

    @Operation(summary = "모임 상세 조회(시간) API", description = "모임 상세 조회(시간) API 입니다.")
    @GetMapping("/{meetingId}")
    public BaseResponse<CandidateTimeResponse.CandidateTimeGetMeetingDetail> getMeetingDetail(
            @AuthenticationMember Member member,
            @PathVariable(value = "meetingId") Long meetingId
    ) {
        return BaseResponse.onSuccess(candidateTimeService.getMeetingDetail(member, meetingId));
    }
}
