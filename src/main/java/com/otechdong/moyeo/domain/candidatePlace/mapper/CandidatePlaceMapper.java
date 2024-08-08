package com.otechdong.moyeo.domain.candidatePlace.mapper;

import com.otechdong.moyeo.domain.meeting.dto.MeetingResponse;
import com.otechdong.moyeo.domain.meeting.entity.Meeting;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.place.entity.CandidatePlace;
import com.otechdong.moyeo.domain.place.entity.Place;
import org.springframework.stereotype.Component;

@Component
public class CandidatePlaceMapper {

    public CandidatePlace toCandidatePlace(Place place, Meeting meeting, Member member) {
        return CandidatePlace.builder()
                .place(place)
                .meeting(meeting)
                .member(member)
                .voteCount(0)
                .build();
    }

    public MeetingResponse.MeetingAddCandidatePlace toMeetingAddCandidatePlace(CandidatePlace candidatePlace) {
        return MeetingResponse.MeetingAddCandidatePlace.builder()
                .candidatePlaceId(candidatePlace.getId())
                .build();
    }

    public MeetingResponse.MeetingDeleteCandidatePlace toMeetingDeleteCandidatePlace(CandidatePlace candidatePlace) {
        return MeetingResponse.MeetingDeleteCandidatePlace.builder()
                .candidatePlaceId(candidatePlace.getId())
                .build();
    }
}
