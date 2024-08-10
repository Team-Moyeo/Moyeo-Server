package com.otechdong.moyeo.domain.candidateTime.service;

import com.otechdong.moyeo.domain.candidateTime.dto.CandidateTimeResponse;
import com.otechdong.moyeo.domain.candidateTime.mapper.CandidateMapper;
import com.otechdong.moyeo.domain.meeting.entity.Meeting;
import com.otechdong.moyeo.domain.candidateTime.entity.CandidateTime;
import com.otechdong.moyeo.domain.meeting.repository.MeetingRepository;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.memberMeeting.entity.MemberMeeting;
import com.otechdong.moyeo.domain.memberMeeting.repository.MemberMeetingRepository;
import com.otechdong.moyeo.domain.voteTime.entity.VoteTime;
import com.otechdong.moyeo.domain.voteTime.mapper.TimeMapper;
import com.otechdong.moyeo.domain.candidateTime.repository.CandidateTimeRepository;
import com.otechdong.moyeo.domain.voteTime.repository.VoteTimeRepository;
import com.otechdong.moyeo.global.exception.RestApiException;
import com.otechdong.moyeo.global.exception.errorCode.MeetingErrorCode;
import com.otechdong.moyeo.global.exception.errorCode.MemberMeetingErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidateTimeServiceImpl implements CandidateTimeService {
    private final CandidateTimeRepository candidateTimeRepository;
    private final MemberMeetingRepository memberMeetingRepository;
    private final MeetingRepository meetingRepository;
    private final VoteTimeRepository voteTimeRepository;
    private final TimeMapper timeMapper;
    private final CandidateMapper candidateMapper;


    @Override
    @Transactional
    public List<CandidateTime> generateCandidateTimes(Meeting meeting) {
        List<CandidateTime> candidateTimes = new ArrayList<>();

        LocalDate currentDate = meeting.getStartDate();
        while (!currentDate.isAfter(meeting.getEndDate())) {
            LocalTime currentTime = meeting.getStartTime();
            while (!currentTime.isAfter(meeting.getEndTime())) {
                CandidateTime candidateTime = candidateMapper.toCandidateTime(meeting, currentDate, currentTime);
                candidateTimes.add(candidateTime);

                // 시간 간격을 30분 단위로 설정
                currentTime = currentTime.plusMinutes(30);
            }
            // 다음 날짜로 이동
            currentDate = currentDate.plusDays(1);
        }

        // CandidateTime 리스트를 저장
        candidateTimeRepository.saveAll(candidateTimes);

        return candidateTimes;
    }

    @Override
    @Transactional
    public List<CandidateTime> generateCandidateTimes2(Meeting meeting) {
        List<CandidateTime> candidateTimes = new ArrayList<>();

        LocalTime currentTime = meeting.getStartTime();
        while (!currentTime.isAfter(meeting.getEndTime())) {
            LocalDate currentDate = meeting.getStartDate();
            while (!currentDate.isAfter(meeting.getEndDate())) {
                CandidateTime candidateTime = candidateMapper.toCandidateTime(meeting, currentDate, currentTime);
                candidateTimes.add(candidateTime);

                // 다음 날짜로 이동
                currentDate = currentDate.plusDays(1);
            }
            // 다음 시간대 30분 추가
            currentTime = currentTime.plusMinutes(30);
        }

        // CandidateTime 리스트를 저장
        candidateTimeRepository.saveAll(candidateTimes);

        return candidateTimes;
    }

    @Override
    public CandidateTimeResponse.CandidateTimeGetMeetingDetail getMeetingDetail(Member member, Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RestApiException(MeetingErrorCode.MEETING_NOT_FOUND));
        MemberMeeting memberMeeting = memberMeetingRepository.findByMemberAndMeeting(member, meeting)
                .orElseThrow(() -> new RestApiException(MemberMeetingErrorCode.MEMBER_MEETING_NOT_FOUND));

        List<VoteTime> myVoteTimes = voteTimeRepository.findByMemberMeeting(memberMeeting);
        List<CandidateTime> totalCandidateTimes = candidateTimeRepository.findByMeetingId(meetingId);

        // TODO : 나중에 따로 함수화시켜서 빼기
        List<String> StringMyVoteTimes = myVoteTimes.stream()
                .map(voteTime -> {
                    CandidateTime candidateTime = voteTime.getCandidateTime();
                    return candidateTime.getDate() + " " + candidateTime.getTime();
                })
                .toList();

        List<CandidateTimeResponse.TotalCandidateTimeInfo> totalCandidateTimeInfos = totalCandidateTimes
                .stream()
                .map(candidateMapper::toTotalCandidateTimeInfo)
                .toList();



        return candidateMapper.toCandidateTimeGetMeetingDetail(StringMyVoteTimes, totalCandidateTimeInfos, meeting.getNumberOfPeople());
    }
}
