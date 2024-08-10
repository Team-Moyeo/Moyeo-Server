package com.otechdong.moyeo.domain.candidatePlace.mapper;

import com.otechdong.moyeo.domain.candidatePlace.dto.CandidatePlaceResponse;
import com.otechdong.moyeo.domain.meeting.dto.MeetingResponse;
import com.otechdong.moyeo.domain.meeting.entity.Meeting;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.candidatePlace.entity.CandidatePlace;
import com.otechdong.moyeo.domain.place.entity.Place;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public CandidatePlaceResponse.CandidatePlaceGetMeetingDetail toCandidatePlaceGetMeetingDetail(
            List<String> myVotedPlaces,
            List<CandidatePlaceResponse.TotalCandidatePlaceInfo> totalCandidatePlaceInfos,
            Long numberOfPeople
    ) {
        return CandidatePlaceResponse.CandidatePlaceGetMeetingDetail.builder()
                .myVotedPlaces(myVotedPlaces)
                .totalCandidatePlaces(totalCandidatePlaceInfos)
                .numberOfPeople(numberOfPeople)
                .build();
    }

    public CandidatePlaceResponse.TotalCandidatePlaceInfo toTotalCandidatePlaceInfo(
            CandidatePlace candidatePlace
    ) {
        return CandidatePlaceResponse.TotalCandidatePlaceInfo.builder()
                .title(candidatePlace.getPlace().getTitle())
                .address(candidatePlace.getPlace().getAddress())
                .longitude(candidatePlace.getPlace().getLongitude())
                .latitude(candidatePlace.getPlace().getLatitude())
                .voteCount(candidatePlace.getVoteCount())
                .build();
    }

    public CandidatePlaceResponse.CandidatePlaceGetMeetingDetail toCandidateTimeGetMeetingDetail(
            List<String> stringMyVotePlaces,
            List<CandidatePlaceResponse.TotalCandidatePlaceInfo> totalCandidatePlaceInfos,
            Long numberOfPeople) {
        return CandidatePlaceResponse.CandidatePlaceGetMeetingDetail.builder()
                .myVotedPlaces(stringMyVotePlaces)
                .totalCandidatePlaces(totalCandidatePlaceInfos)
                .numberOfPeople(numberOfPeople)
                .build();
    }
}
