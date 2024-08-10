package com.otechdong.moyeo.domain.voteTime.service;

import com.otechdong.moyeo.domain.memberMeeting.entity.MemberMeeting;
import com.otechdong.moyeo.domain.candidateTime.entity.CandidateTime;
import com.otechdong.moyeo.domain.voteTime.entity.VoteTime;

public interface VoteTimeService {
    VoteTime generateVoteTime(MemberMeeting memberMeeting, CandidateTime candidateTime);
}
