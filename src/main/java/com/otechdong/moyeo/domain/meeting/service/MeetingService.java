package com.otechdong.moyeo.domain.meeting.service;

import com.otechdong.moyeo.domain.meeting.dto.MeetingRequest;
import com.otechdong.moyeo.domain.meeting.dto.MeetingResponse;
import com.otechdong.moyeo.domain.meeting.entity.MeetingStatus;
import com.otechdong.moyeo.domain.member.entity.Member;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface MeetingService {

    MeetingResponse.MeetingCreate createMeeting(
            Member member,
            String title,
            LocalDate startDate,
            LocalTime startTime,
            LocalDate endDate,
            LocalTime endTime,
            List<LocalDateTime> fixedTimes,
            List<MeetingRequest.MeetingCreatePlace> candidatePlaces,
            MeetingRequest.MeetingCreatePlace fixedPlace,
            LocalDateTime deadline);

    MeetingResponse.MeetingGetList getMeetingsByMeetingStatus(Member member, MeetingStatus meetingStatus);

    MeetingResponse.MeetingDelete deleteMeeting(Member member, Long meetingId);

    MeetingResponse.MeetingJoinWithInviteCode joinMeetingWithInviteCode(Member member, String inviteCode);

    MeetingResponse.MeetingGetInviteCode getInviteCode(Long meetingId);

    MeetingResponse.MeetingGetDetail getMeetingDetail(Member member, Long meetingId);

    MeetingResponse.MeetingVoteConfirm voteConfirm(Member member, Long meetingId, List<Long> candidateTimeIds, List<Long> candidatePlaceIds);

    MeetingResponse.MeetingVoteConfirm voteConfirmWithValues(Member member, Long meetingId, List<String> candidateTimeValues, List<String> candidatePlaceValues);

    MeetingResponse.MeetingVoteUpdate voteUpdate(Member member, Long meetingId, List<Long> candidateTimeIds, List<Long> candidatePlaceIds);

    MeetingResponse.MeetingVoteUpdate voteUpdateWithValues(Member member, Long meetingId, List<String> candidateTimes, List<String> candidatePlaces);

    MeetingResponse.MeetingFix fixMeeting(Member member, Long meetingId, List<String> fixedTimes, MeetingRequest.FixPlaceInfo fixedPlace);
}
