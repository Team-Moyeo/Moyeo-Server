package com.otechdong.moyeo.domain.place.service;

import com.otechdong.moyeo.domain.memberMeeting.entity.MemberMeeting;
import com.otechdong.moyeo.domain.place.entity.CandidatePlace;
import com.otechdong.moyeo.domain.place.entity.VotePlace;
import com.otechdong.moyeo.domain.time.entity.CandidateTime;
import com.otechdong.moyeo.domain.time.entity.VoteTime;

public interface VotePlaceService {
    VotePlace generateVotePlace(MemberMeeting memberMeeting, CandidatePlace candidatePlace);
}
