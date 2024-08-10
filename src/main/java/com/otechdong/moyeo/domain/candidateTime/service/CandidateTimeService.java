package com.otechdong.moyeo.domain.candidateTime.service;

import com.otechdong.moyeo.domain.meeting.entity.Meeting;
import com.otechdong.moyeo.domain.candidateTime.entity.CandidateTime;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CandidateTimeService {
    @Transactional
    List<CandidateTime> generateCandidateTimes(Meeting meeting);

    @Transactional
    List<CandidateTime> generateCandidateTimes2(Meeting meeting);
}
