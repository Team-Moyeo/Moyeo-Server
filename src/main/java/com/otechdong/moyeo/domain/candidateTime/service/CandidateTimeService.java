package com.otechdong.moyeo.domain.candidateTime.service;

import com.otechdong.moyeo.domain.candidateTime.dto.CandidateTimeResponse;
import com.otechdong.moyeo.domain.meeting.entity.Meeting;
import com.otechdong.moyeo.domain.candidateTime.entity.CandidateTime;
import com.otechdong.moyeo.domain.member.entity.Member;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CandidateTimeService {
    @Transactional
    List<CandidateTime> generateCandidateTimes(Meeting meeting);

    @Transactional
    List<CandidateTime> generateCandidateTimes2(Meeting meeting);

    CandidateTimeResponse.CandidateTimeGetMeetingDetail getMeetingDetail(Member member, Long meetingId);
}
