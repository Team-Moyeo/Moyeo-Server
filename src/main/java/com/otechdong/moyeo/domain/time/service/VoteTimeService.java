package com.otechdong.moyeo.domain.time.service;

import com.otechdong.moyeo.domain.meeting.entity.Meeting;
import com.otechdong.moyeo.domain.memberMeeting.entity.MemberMeeting;
import com.otechdong.moyeo.domain.time.entity.CandidateTime;
import com.otechdong.moyeo.domain.time.entity.VoteTime;

import java.util.List;

public interface VoteTimeService {
    VoteTime generateVoteTime(MemberMeeting memberMeeting, CandidateTime candidateTime);
}
