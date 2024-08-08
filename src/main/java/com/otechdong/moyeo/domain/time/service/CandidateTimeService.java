package com.otechdong.moyeo.domain.time.service;

import com.otechdong.moyeo.domain.meeting.entity.Meeting;
import com.otechdong.moyeo.domain.time.entity.CandidateTime;

import java.util.List;

public interface CandidateTimeService {
    List<CandidateTime> generateCandidateTimes(Meeting meeting);
}
