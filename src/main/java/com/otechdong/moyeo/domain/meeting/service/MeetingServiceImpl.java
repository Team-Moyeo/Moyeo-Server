package com.otechdong.moyeo.domain.meeting.service;

import com.otechdong.moyeo.domain.meeting.dto.MeetingRequest;
import com.otechdong.moyeo.domain.meeting.dto.MeetingResponse;
import com.otechdong.moyeo.domain.meeting.entity.Meeting;
import com.otechdong.moyeo.domain.meeting.entity.MeetingStatus;
import com.otechdong.moyeo.domain.meeting.mapper.MeetingMapper;
import com.otechdong.moyeo.domain.meeting.repository.MeetingRepository;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.member.entity.Role;
import com.otechdong.moyeo.domain.member.repository.MemberRepository;
import com.otechdong.moyeo.domain.memberMeeting.entity.MemberMeeting;
import com.otechdong.moyeo.domain.memberMeeting.mapper.MemberMeetingMapper;
import com.otechdong.moyeo.domain.memberMeeting.repository.MemberMeetingRepository;
import com.otechdong.moyeo.domain.memberMeeting.service.MemberMeetingService;
import com.otechdong.moyeo.domain.candidatePlace.entity.CandidatePlace;
import com.otechdong.moyeo.domain.place.entity.Place;
import com.otechdong.moyeo.domain.place.entity.VotePlace;
import com.otechdong.moyeo.domain.candidatePlace.mapper.CandidatePlaceMapper;
import com.otechdong.moyeo.domain.place.mapper.PlaceMapper;
import com.otechdong.moyeo.domain.candidatePlace.repository.CandidatePlaceRepository;
import com.otechdong.moyeo.domain.place.repository.PlaceRepository;
import com.otechdong.moyeo.domain.place.repository.VotePlaceRepository;
import com.otechdong.moyeo.domain.place.service.PlaceService;
import com.otechdong.moyeo.domain.place.service.VotePlaceService;
import com.otechdong.moyeo.domain.candidateTime.entity.CandidateTime;
import com.otechdong.moyeo.domain.voteTime.entity.VoteTime;
import com.otechdong.moyeo.domain.voteTime.mapper.TimeMapper;
import com.otechdong.moyeo.domain.candidateTime.repository.CandidateTimeRepository;
import com.otechdong.moyeo.domain.voteTime.repository.VoteTimeRepository;
import com.otechdong.moyeo.domain.candidateTime.service.CandidateTimeService;
import com.otechdong.moyeo.domain.voteTime.service.VoteTimeService;
import com.otechdong.moyeo.global.exception.RestApiException;
import com.otechdong.moyeo.global.exception.errorCode.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private final PlaceRepository placeRepository;
    private final MemberRepository memberRepository;
    private final CandidatePlaceRepository candidatePlaceRepository;
    private final CandidateTimeRepository candidateTimeRepository;
    private final MemberMeetingRepository memberMeetingRepository;
    private final VoteTimeRepository voteTimeRepository;
    private final VotePlaceRepository votePlaceRepository;

    private final MemberMeetingService memberMeetingService;
    private final CandidateTimeService candidateTimeService;
    private final VoteTimeService voteTimeService;
    private final VotePlaceService votePlaceService;
    private final PlaceService placeService;

    private final MeetingMapper meetingMapper;
    private final CandidatePlaceMapper candidatePlaceMapper;
    private final TimeMapper timeMapper;
    private final PlaceMapper placeMapper;
    private final MemberMeetingMapper memberMeetingMapper;

    @Override
    @Transactional
    public MeetingResponse.MeetingCreate createMeeting(
            Member member,
            String title,
            LocalDate startDate,
            LocalTime startTime,
            LocalDate endDate,
            LocalTime endTime,
            List<LocalDateTime> fixedTimes,
            List<MeetingRequest.MeetingCreatePlace> candidatePlaces,
            MeetingRequest.MeetingCreatePlace fixedPlace,
            LocalDateTime deadline) {


        // 초대 코드 중복 검사
        String inviteCode = generateInviteCode();
        while (existsDuplicatedInviteCode(inviteCode)) {
            inviteCode = generateInviteCode();
        }

        Meeting newMeeting = meetingMapper.toMeeting(title, startDate, startTime, endDate, endTime, null, null, deadline, inviteCode);
        meetingRepository.save(newMeeting);
        List<CandidateTime> candidateTimes = candidateTimeService.generateCandidateTimes2(newMeeting);

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

        // 입력받은 확정 시간이 있는 경우
        if (fixedTimes != null) {
            newMeeting.updateFixedTime(fixedTimes);
        }

        // 입력받은 후보 장소가 있는 경우
        if (candidatePlaces != null) {
            List<CandidatePlace> meetingCandidatePlaces = candidatePlaces
                    .stream()
                    .map(candidatePlace -> {
                        Optional<Place> optionalPlace = placeRepository.findByTitleAndAddressAndLatitudeAndLongitude(
                                candidatePlace.getTitle(),
                                candidatePlace.getAddress(),
                                candidatePlace.getLatitude(),
                                candidatePlace.getLongitude()
                        );

                        Place place = optionalPlace.orElseGet(() -> {
                            Place newPlace = placeMapper.toPlace(
                                    member,
                                    candidatePlace.getTitle(),
                                    candidatePlace.getAddress(),
                                    candidatePlace.getLatitude(),
                                    candidatePlace.getLongitude()
                            );
                            return placeRepository.save(newPlace);
                        });

                        return candidatePlaceMapper.toCandidatePlace(place, newMeeting, member);
                    })
                    .toList();
            candidatePlaceRepository.saveAll(meetingCandidatePlaces);
        }


        memberMeetingService.createMemberMeeting(member, newMeeting, Role.OWNER);

        // TODO : 이 부분 구현하기

        return meetingMapper.toMeetingCreate(newMeeting);
    }

    @Override
    public MeetingResponse.MeetingGetList getMeetingsByMeetingStatus(Member member, MeetingStatus meetingStatus) {
        List<Meeting> meetings;
        if (meetingStatus == null) {
            meetings = meetingRepository.findMeetingsByMemberId(member.getId());
        } else {
            meetings = meetingRepository.findMeetingsByMemberIdAndMeetingStatus(member.getId(), meetingStatus);
        }
        return meetingMapper.toMeetingGetList(meetings.stream()
                .map(meetingMapper::toMeetingGetListMeetingInfo)
                .toList());
    }

    @Override
    @Transactional
    public MeetingResponse.MeetingDelete deleteMeeting(Member member, Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RestApiException(MeetingErrorCode.MEETING_NOT_FOUND));

        // 모임 장이 아니면 에러 반환
        if (!isOwnerOfMeeting(member, meeting)) {
            throw new RestApiException(MeetingErrorCode.MEETING_ACCESS_DENIED);
        }
        meeting.delete();
        return meetingMapper.toMeetingDelete(meeting);
    }

    @Override
    public MeetingResponse.MeetingJoinWithInviteCode joinMeetingWithInviteCode(Member member, String inviteCode) {
        Meeting meeting = meetingRepository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new RestApiException(MeetingErrorCode.MEETING_NOT_FOUND));

        // 이미 가입된 모임일 경우
        if (memberMeetingRepository.existsByMemberAndMeeting(member, meeting)) {
            throw new RestApiException(MemberMeetingErrorCode.MEMBER_MEETING_ALREADY_EXIST);
        }
        MemberMeeting memberMeeting = memberMeetingMapper.toMemberMeeting(member, meeting, Role.PARTICIPANTS);
        meeting.increaseNumberOfPeople();
        memberMeetingRepository.save(memberMeeting);
        return meetingMapper.toMeetingJoinWithInviteCode(meeting);
    }

    @Override
    public MeetingResponse.MeetingGetInviteCode getInviteCode(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RestApiException(MeetingErrorCode.MEETING_NOT_FOUND));

        return meetingMapper.toMeetingGetInviteCode(meeting.getInviteCode());
    }

    @Override
    public MeetingResponse.MeetingGetDetail getMeetingDetail(Member member, Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RestApiException(MeetingErrorCode.MEETING_NOT_FOUND));

        MemberMeeting memberMeeting = memberMeetingRepository.findByMemberAndMeeting(member, meeting)
                .orElseThrow(() -> new RestApiException(MemberMeetingErrorCode.MEMBER_MEETING_NOT_FOUND));


        return meetingMapper.toMeetingGetDetail(meeting, memberMeeting);
    }

    @Override
    @Transactional
    public MeetingResponse.MeetingVoteConfirm voteConfirm(Member member, Long meetingId, List<Long> candidateTimeIds, List<Long> candidatePlaceIds) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RestApiException(MeetingErrorCode.MEETING_NOT_FOUND));

        MemberMeeting memberMeeting = memberMeetingRepository.findByMemberAndMeeting(member, meeting)
                .orElseThrow(() -> new RestApiException(MemberMeetingErrorCode.MEMBER_MEETING_NOT_FOUND));

        List<Long> voteTimeIds = new ArrayList<>();
        List<Long> votePlaceIds = new ArrayList<>();

        // 시간 투표 반영(CandidateTime.id & MemberMeeting.id가 일치하는 리스트를 찾아서 투표)
        // TODO: 일치하는 CandidateTime.id와 일치하지 않는 CandidateTime.id가 같이 들어왔을 때, 일치하는 것만 반영됨 -> 에러로 처리할 것인지 고민
        List<CandidateTime> candidateTimes = candidateTimeRepository.findByMeetingIdAndIds(meetingId, candidateTimeIds);
        if (!candidateTimeIds.isEmpty() && candidateTimes.isEmpty()) {
            throw new RestApiException(CandidateTimeErrorCode.CANDIDATE_TIME_NOT_FOUND);
        }
        for (CandidateTime candidateTime : candidateTimes) {
            VoteTime voteTime = voteTimeService.generateVoteTime(memberMeeting, candidateTime);
            voteTimeIds.add(voteTime.getId());
        }

        // 장소 투표 반영(CandidatePlace.id & MemberMeeting.id가 일치하는 리스트를 찾아서 투표)
        // TODO: 일치하는 CandidatePlace.id와 일치하지 않는 CandidatePlace.id가 같이 들어왔을 때, 일치하는 것만 반영됨 -> 에러로 처리할 것인지 고민
        List<CandidatePlace> candidatePlaces = candidatePlaceRepository.findByMeetingIdAndIds(meetingId, candidatePlaceIds);
        if (!candidatePlaceIds.isEmpty() && candidatePlaces.isEmpty()) {
            throw new RestApiException(CandidatePlaceErrorCode.CANDIDATE_PLACE_NOT_FOUND);
        }
        for (CandidatePlace candidatePlace : candidatePlaces) {
            VotePlace votePlace = votePlaceService.generateVotePlace(memberMeeting, candidatePlace);
            votePlaceIds.add(votePlace.getId());
        }

        return meetingMapper.toMeetingVoteConfirm(meeting.getId(), voteTimeIds, votePlaceIds);
    }

    // id값이 아니라 value기준으로 조회
    @Override
    @Transactional
    public MeetingResponse.MeetingVoteConfirm voteConfirmWithValues(Member member, Long meetingId, List<String> candidateTimeValues, List<String> candidatePlaceValues) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RestApiException(MeetingErrorCode.MEETING_NOT_FOUND));

        MemberMeeting memberMeeting = memberMeetingRepository.findByMemberAndMeeting(member, meeting)
                .orElseThrow(() -> new RestApiException(MemberMeetingErrorCode.MEMBER_MEETING_NOT_FOUND));

        List<Long> voteTimeIds = new ArrayList<>();
        List<Long> votePlaceIds = new ArrayList<>();

        // 시간 투표 반영(CandidateTime.id & MemberMeeting.id가 일치하는 리스트를 찾아서 투표)
        // TODO: 일치하는 CandidateTime.id와 일치하지 않는 CandidateTime.id가 같이 들어왔을 때, 일치하는 것만 반영됨 -> 에러로 처리할 것인지 고민

        List<List<String>> dateTimeStrings = splitDateAndTimeList(candidateTimeValues);

        List<String> dateStrings = dateTimeStrings.get(0);
        List<String> timeStrings = dateTimeStrings.get(1);

        List<CandidateTime> candidateTimes = new ArrayList<>();
        for (int i = 0; i < candidateTimeValues.size(); i++) {
            LocalDate date = LocalDate.parse(dateStrings.get(i));
            LocalTime time = LocalTime.parse(timeStrings.get(i));

            CandidateTime foundTime = candidateTimeRepository.findByMeetingIdAndDateTime(meetingId, date, time)
                            .orElseThrow(() -> new RestApiException(CandidateTimeErrorCode.CANDIDATE_TIME_NOT_FOUND));

            candidateTimes.add(foundTime);
        }

        if (!candidateTimeValues.isEmpty() && candidateTimes.isEmpty()) {
            throw new RestApiException(CandidateTimeErrorCode.CANDIDATE_TIME_NOT_FOUND);
        }
        for (CandidateTime candidateTime : candidateTimes) {
            VoteTime voteTime = voteTimeService.generateVoteTime(memberMeeting, candidateTime);
            voteTimeIds.add(voteTime.getId());
        }

        // 장소 투표 반영(CandidatePlace.id & MemberMeeting.id가 일치하는 리스트를 찾아서 투표)
        // TODO: 일치하는 CandidatePlace.id와 일치하지 않는 CandidatePlace.id가 같이 들어왔을 때, 일치하는 것만 반영됨 -> 에러로 처리할 것인지 고민
        List<CandidatePlace> candidatePlaces = candidatePlaceRepository.findByMeetingIdAndValues(meetingId, candidatePlaceValues);
        if (!candidatePlaceValues.isEmpty() && candidatePlaces.isEmpty()) {
            throw new RestApiException(CandidatePlaceErrorCode.CANDIDATE_PLACE_NOT_FOUND);
        }
        for (CandidatePlace candidatePlace : candidatePlaces) {
            VotePlace votePlace = votePlaceService.generateVotePlace(memberMeeting, candidatePlace);
            votePlaceIds.add(votePlace.getId());
        }

        return meetingMapper.toMeetingVoteConfirm(meeting.getId(), voteTimeIds, votePlaceIds);
    }

    @Override
    @Transactional
    public MeetingResponse.MeetingVoteUpdate voteUpdate(Member member, Long meetingId, List<Long> candidateTimeIds, List<Long> candidatePlaceIds) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RestApiException(MeetingErrorCode.MEETING_NOT_FOUND));

        MemberMeeting memberMeeting = memberMeetingRepository.findByMemberAndMeeting(member, meeting)
                .orElseThrow(() -> new RestApiException(MemberMeetingErrorCode.MEMBER_MEETING_NOT_FOUND));

        // 해당 모임에서 내가 한 투표 가져오기
        List<VoteTime> myVoteTimes = voteTimeRepository.findByMemberMeeting(memberMeeting);
        List<VotePlace> myVotePlaces = votePlaceRepository.findByMemberMeeting(memberMeeting);

        // TODO : 추후에 효율적인 코드로 고쳐보기 -> 지금은 전부 삭제 및 다시 전부 재생성
        for (VoteTime voteTime : myVoteTimes) {
            voteTime.getCandidateTime().decreaseVoteCount();
            voteTime.delete();
        }

        for (VotePlace votePlace : myVotePlaces) {
            votePlace.getCandidatePlace().decreaseVoteCount();
            votePlace.delete();
        }

        return meetingMapper.toMeetingVoteUpdate(voteConfirm(member, meetingId, candidateTimeIds, candidatePlaceIds));
    }

    // id값이 아니라 value기준으로 조회
    @Override
    @Transactional
    public MeetingResponse.MeetingVoteUpdate voteUpdateWithValues(Member member, Long meetingId, List<String> candidateTimes, List<String> candidatePlaces) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RestApiException(MeetingErrorCode.MEETING_NOT_FOUND));

        MemberMeeting memberMeeting = memberMeetingRepository.findByMemberAndMeeting(member, meeting)
                .orElseThrow(() -> new RestApiException(MemberMeetingErrorCode.MEMBER_MEETING_NOT_FOUND));

        // 해당 모임에서 내가 한 투표 가져오기
        List<VoteTime> myVoteTimes = voteTimeRepository.findByMemberMeeting(memberMeeting);
        List<VotePlace> myVotePlaces = votePlaceRepository.findByMemberMeeting(memberMeeting);

        // TODO : 추후에 효율적인 코드로 고쳐보기 -> 지금은 전부 삭제 및 다시 전부 재생성
        for (VoteTime voteTime : myVoteTimes) {
            voteTime.getCandidateTime().decreaseVoteCount();
            voteTime.delete();
        }

        for (VotePlace votePlace : myVotePlaces) {
            votePlace.getCandidatePlace().decreaseVoteCount();
            votePlace.delete();
        }

        return meetingMapper.toMeetingVoteUpdate(voteConfirmWithValues(member, meetingId, candidateTimes, candidatePlaces));
    }

    public Boolean isOwnerOfMeeting(Member member, Meeting meeting) {
        MemberMeeting memberMeeting = memberMeetingRepository.findByMemberAndMeeting(member, meeting)
                .orElseThrow(() -> new RestApiException(MemberMeetingErrorCode.MEMBER_MEETING_NOT_FOUND));
        return memberMeeting.getRole() == Role.OWNER;
    }

    public String generateInviteCode() {

        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int CODE_LENGTH = 8;
        SecureRandom RANDOM = new SecureRandom();

        StringBuilder inviteCode = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            inviteCode.append(CHARACTERS.charAt(randomIndex));
        }
        return inviteCode.toString();
    }

    // 초대 코드 중복 검사 함수
    public Boolean existsDuplicatedInviteCode(String inviteCode) {
        return meetingRepository.existsByInviteCode(inviteCode);
    }

    public List<List<String>> splitDateAndTimeList(List<String> dateTimeList) {
        List<String> dateStrings = new ArrayList<>();
        List<String> timeStrings = new ArrayList<>();
        List<List<String>> newList = new ArrayList<>();

        for (String dateTime : dateTimeList) {
            String[] parts = dateTime.split(" ");
            dateStrings.add(parts[0]); // 날짜 부분
            timeStrings.add(parts[1]); // 시간 부분
        }
        newList.add(dateStrings);
        newList.add(timeStrings);
        return newList;
    }

}
