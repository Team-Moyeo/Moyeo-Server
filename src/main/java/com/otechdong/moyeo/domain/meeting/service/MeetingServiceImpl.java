package com.otechdong.moyeo.domain.meeting.service;

import com.otechdong.moyeo.domain.meeting.dto.MeetingRequest;
import com.otechdong.moyeo.domain.meeting.dto.MeetingResponse;
import com.otechdong.moyeo.domain.meeting.entity.Meeting;
import com.otechdong.moyeo.domain.meeting.mapper.MeetingMapper;
import com.otechdong.moyeo.domain.meeting.repository.MeetingRepository;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.member.entity.Role;
import com.otechdong.moyeo.domain.member.repository.MemberRepository;
import com.otechdong.moyeo.domain.memberMeeting.service.MemberMeetingService;
import com.otechdong.moyeo.domain.place.entity.CandidatePlace;
import com.otechdong.moyeo.domain.place.entity.Place;
import com.otechdong.moyeo.domain.place.mapper.CandidatePlaceMapper;
import com.otechdong.moyeo.domain.place.mapper.PlaceMapper;
import com.otechdong.moyeo.domain.place.repository.PlaceRepository;
import com.otechdong.moyeo.domain.place.service.PlaceService;
import com.otechdong.moyeo.domain.time.mapper.TimeMapper;
import com.otechdong.moyeo.global.exception.RestApiException;
import com.otechdong.moyeo.global.exception.errorCode.MeetingErrorCode;
import com.otechdong.moyeo.global.exception.errorCode.PlaceErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private final PlaceRepository placeRepository;
    private final MemberRepository memberRepository;
    private final MemberMeetingService memberMeetingService;
    private final PlaceService placeService;
    private final MeetingMapper meetingMapper;
    private final CandidatePlaceMapper candidatePlaceMapper;
    private final TimeMapper timeMapper;
    private final PlaceMapper placeMapper;

    @Override
    @Transactional
    public MeetingResponse.MeetingCreate createMeeting(
            Member member,
            String title,
            LocalDate startDate,
            LocalTime startTime,
            LocalDate endDate,
            LocalTime endTime,
            List<MeetingRequest.MeetingCreateTime> fixedTimes,
            List<MeetingRequest.MeetingCreatePlace> candidatePlaces,
            MeetingRequest.MeetingCreatePlace fixedPlace,
            LocalDateTime deadline) {

        Meeting newMeeting = meetingMapper.toMeeting(title, startDate, startTime, endDate, endTime, null, null, null, deadline);
        meetingRepository.save(newMeeting);


        // 입력받은 확정 장소가 있는 경우
        if (fixedPlace != null) {
            Optional<Place> optionalFixedPlace = placeRepository.findByTitleAndAddressAndLatitudeAndLongitude(fixedPlace.getTitle(), fixedPlace.getAddress(), fixedPlace.getLatitude(), fixedPlace.getLongitude());

            Place place;
            if (optionalFixedPlace.isPresent()) {    // 1. 입력받은 확정 장소가 이전에 멤버가 등록해놓은 장소일 경우
                place = optionalFixedPlace.get();
            } else {                                // 2. 입력받은 확정 장소가 이전에 멤버가 등록하지 않은 장소일 경우
                place = placeRepository.save(placeMapper.toPlace(member, fixedPlace.getTitle(), fixedPlace.getAddress(), fixedPlace.getLatitude(), fixedPlace.getLongitude()));
            }
            newMeeting.updateFixedPlace(place);
        }
        

        memberMeetingService.createMemberMeeting(member, newMeeting, Role.OWNER);

        // TODO : 이 부분 구현하기

        return meetingMapper.toMeetingCreate(newMeeting);
    }

    @Override
    public MeetingResponse.MeetingAddCandidatePlace addCandidatePlace(Member member, Long meetingId, Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new RestApiException(PlaceErrorCode.PLACE_NOT_FOUND));
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RestApiException(MeetingErrorCode.MEETING_NOT_FOUND));
        CandidatePlace newCandidatePlace = candidatePlaceMapper.toCandidatePlace(place, meeting, member);
        return candidatePlaceMapper.toMeetingAddCandidatePlace(newCandidatePlace);
    }
}