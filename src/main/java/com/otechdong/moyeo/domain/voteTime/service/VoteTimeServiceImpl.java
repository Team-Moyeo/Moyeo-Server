package com.otechdong.moyeo.domain.voteTime.service;

import com.otechdong.moyeo.domain.memberMeeting.entity.MemberMeeting;
import com.otechdong.moyeo.domain.candidateTime.entity.CandidateTime;
import com.otechdong.moyeo.domain.voteTime.entity.VoteTime;
import com.otechdong.moyeo.domain.voteTime.mapper.TimeMapper;
import com.otechdong.moyeo.domain.voteTime.repository.VoteTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteTimeServiceImpl implements VoteTimeService{

    private final VoteTimeRepository voteTimeRepository;
    private final TimeMapper timeMapper;


    @Override
    @Transactional
    public VoteTime generateVoteTime(MemberMeeting memberMeeting, CandidateTime candidateTime) {
        Optional<VoteTime> optionalVoteTime = voteTimeRepository.findByMemberMeetingAndCandidateTime(memberMeeting, candidateTime);
        // 이미 투표했으면 기존 값을 반환.
        if (optionalVoteTime.isPresent()) {
            return optionalVoteTime.get();
        }
        // 없으면 새로운 투표 생성.
        VoteTime voteTime = timeMapper.toVoteTime(memberMeeting, candidateTime);
        candidateTime.increaseVoteCount();
        voteTimeRepository.save(voteTime);
        return voteTime;
    }
}
