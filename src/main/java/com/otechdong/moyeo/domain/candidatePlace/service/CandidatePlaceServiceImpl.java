package com.otechdong.moyeo.domain.candidatePlace.service;

import com.otechdong.moyeo.domain.meeting.dto.MeetingResponse;
import com.otechdong.moyeo.domain.meeting.entity.Meeting;
import com.otechdong.moyeo.domain.meeting.repository.MeetingRepository;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.place.entity.CandidatePlace;
import com.otechdong.moyeo.domain.place.entity.Place;
import com.otechdong.moyeo.domain.place.mapper.CandidatePlaceMapper;
import com.otechdong.moyeo.domain.place.repository.CandidatePlaceRepository;
import com.otechdong.moyeo.domain.place.repository.PlaceRepository;
import com.otechdong.moyeo.global.exception.RestApiException;
import com.otechdong.moyeo.global.exception.errorCode.CandidatePlaceErrorCode;
import com.otechdong.moyeo.global.exception.errorCode.MeetingErrorCode;
import com.otechdong.moyeo.global.exception.errorCode.PlaceErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CandidatePlaceServiceImpl implements CandidatePlaceService{

    private final PlaceRepository placeRepository;
    private final MeetingRepository meetingRepository;
    private final CandidatePlaceRepository candidatePlaceRepository;

    private final CandidatePlaceMapper candidatePlaceMapper;
    @Override
    public MeetingResponse.MeetingAddCandidatePlace addCandidatePlace(Member member, Long meetingId, Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new RestApiException(PlaceErrorCode.PLACE_NOT_FOUND));
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RestApiException(MeetingErrorCode.MEETING_NOT_FOUND));

        // 이미 있으면 예외처리
        if (candidatePlaceRepository.existsByMeetingAndPlace(meeting, place)) {
            throw new RestApiException(CandidatePlaceErrorCode.CANDIDATE_PLACE_ALREADY_EXIST);
        }
        CandidatePlace newCandidatePlace = candidatePlaceMapper.toCandidatePlace(place, meeting, member);

        candidatePlaceRepository.save(newCandidatePlace);
        return candidatePlaceMapper.toMeetingAddCandidatePlace(newCandidatePlace);
    }
}
