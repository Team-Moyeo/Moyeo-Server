package com.otechdong.moyeo.domain.candidatePlace.service;

import com.otechdong.moyeo.domain.candidatePlace.dto.CandidatePlaceResponse;
import com.otechdong.moyeo.domain.meeting.dto.MeetingResponse;
import com.otechdong.moyeo.domain.member.entity.Member;

public interface CandidatePlaceService {
    MeetingResponse.MeetingAddCandidatePlace addCandidatePlace(
            Member member,
            Long meetingId,
            Long placeId);

    MeetingResponse.MeetingDeleteCandidatePlace deleteCandidatePlace(
            Member member,
            Long meetingId,
            Long candidatePlaceId);

    CandidatePlaceResponse.CandidatePlaceGetMeetingDetail getMeetingDetail(Member member, Long meetingId);
}
