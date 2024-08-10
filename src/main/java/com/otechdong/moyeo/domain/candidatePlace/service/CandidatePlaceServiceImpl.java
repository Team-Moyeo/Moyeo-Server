package com.otechdong.moyeo.domain.candidatePlace.service;

import com.otechdong.moyeo.domain.candidatePlace.dto.CandidatePlaceResponse;
import com.otechdong.moyeo.domain.meeting.dto.MeetingResponse;
import com.otechdong.moyeo.domain.meeting.entity.Meeting;
import com.otechdong.moyeo.domain.meeting.repository.MeetingRepository;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.member.entity.Role;
import com.otechdong.moyeo.domain.memberMeeting.entity.MemberMeeting;
import com.otechdong.moyeo.domain.memberMeeting.repository.MemberMeetingRepository;
import com.otechdong.moyeo.domain.candidatePlace.entity.CandidatePlace;
import com.otechdong.moyeo.domain.place.entity.Place;
import com.otechdong.moyeo.domain.candidatePlace.mapper.CandidatePlaceMapper;
import com.otechdong.moyeo.domain.place.entity.VotePlace;
import com.otechdong.moyeo.domain.candidatePlace.repository.CandidatePlaceRepository;
import com.otechdong.moyeo.domain.place.repository.PlaceRepository;
import com.otechdong.moyeo.domain.place.repository.VotePlaceRepository;
import com.otechdong.moyeo.global.exception.RestApiException;
import com.otechdong.moyeo.global.exception.errorCode.CandidatePlaceErrorCode;
import com.otechdong.moyeo.global.exception.errorCode.MeetingErrorCode;
import com.otechdong.moyeo.global.exception.errorCode.MemberMeetingErrorCode;
import com.otechdong.moyeo.global.exception.errorCode.PlaceErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidatePlaceServiceImpl implements CandidatePlaceService{

    private final PlaceRepository placeRepository;
    private final VotePlaceRepository votePlaceRepository;
    private final MeetingRepository meetingRepository;
    private final CandidatePlaceRepository candidatePlaceRepository;

    private final MemberMeetingRepository memberMeetingRepository;

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

    @Override
    @Transactional
    public MeetingResponse.MeetingDeleteCandidatePlace deleteCandidatePlace(Member member, Long meetingId, Long candidatePlaceId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RestApiException(MeetingErrorCode.MEETING_NOT_FOUND));
        MemberMeeting memberMeeting = memberMeetingRepository.findByMemberAndMeeting(member, meeting)
                .orElseThrow(() -> new RestApiException(MemberMeetingErrorCode.MEMBER_MEETING_NOT_FOUND));

        CandidatePlace candidatePlace = candidatePlaceRepository.findByMeetingIdAndId(meetingId, candidatePlaceId)
                .orElseThrow(() -> new RestApiException(CandidatePlaceErrorCode.CANDIDATE_PLACE_NOT_FOUND));

        // 삭제 권한이 있는 사용자만 삭제(후보지를 올린 본인 또는 모임장)
        if (candidatePlace.getMember().equals(member) || memberMeeting.getRole().equals(Role.OWNER)) {
            candidatePlace.delete();
        } else {
            throw new RestApiException(CandidatePlaceErrorCode.CANDIDATE_PLACE_PERMISSION_DENIED);
        }
        return candidatePlaceMapper.toMeetingDeleteCandidatePlace(candidatePlace);
    }

    @Override
    public CandidatePlaceResponse.CandidatePlaceGetMeetingDetail getMeetingDetail(Member member, Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RestApiException(MeetingErrorCode.MEETING_NOT_FOUND));
        MemberMeeting memberMeeting = memberMeetingRepository.findByMemberAndMeeting(member, meeting)
                .orElseThrow(() -> new RestApiException(MemberMeetingErrorCode.MEMBER_MEETING_NOT_FOUND));

        List<VotePlace> myVotePlaces = votePlaceRepository.findByMemberMeeting(memberMeeting);
        List<CandidatePlace> totalCandidatePlaces = candidatePlaceRepository.findByMeetingId(meetingId);

        // TODO : 나중에 따로 함수화시켜서 빼기
        List<String> StringMyVotePlaces = myVotePlaces.stream()
                .map(votePlace -> {
                    CandidatePlace candidatePlace = votePlace.getCandidatePlace();
                    return candidatePlace.getPlace().getTitle();
                })
                .toList();

        List<CandidatePlaceResponse.TotalCandidatePlaceInfo> totalCandidatePlaceInfos = totalCandidatePlaces
                .stream()
                .map(candidatePlaceMapper::toTotalCandidatePlaceInfo)
                .toList();



        return candidatePlaceMapper.toCandidateTimeGetMeetingDetail(StringMyVotePlaces, totalCandidatePlaceInfos, meeting.getNumberOfPeople());
    }
}
