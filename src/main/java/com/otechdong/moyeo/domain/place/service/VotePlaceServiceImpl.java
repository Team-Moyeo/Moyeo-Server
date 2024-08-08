package com.otechdong.moyeo.domain.place.service;

import com.otechdong.moyeo.domain.memberMeeting.entity.MemberMeeting;
import com.otechdong.moyeo.domain.place.entity.CandidatePlace;
import com.otechdong.moyeo.domain.place.entity.VotePlace;
import com.otechdong.moyeo.domain.place.mapper.PlaceMapper;
import com.otechdong.moyeo.domain.place.repository.VotePlaceRepository;
import com.otechdong.moyeo.domain.time.entity.VoteTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VotePlaceServiceImpl implements VotePlaceService {

    private final VotePlaceRepository votePlaceRepository;
    private final PlaceMapper placeMapper;

    @Override
    @Transactional
    public VotePlace generateVotePlace(MemberMeeting memberMeeting, CandidatePlace candidatePlace) {
        Optional<VotePlace> optionalVotePlace = votePlaceRepository.findByMemberMeetingAndCandidatePlace(memberMeeting, candidatePlace);
        // 이미 투표했으면 기존 값을 반환.
        if (optionalVotePlace.isPresent()) {
            return optionalVotePlace.get();
        }
        // 없으면 새로운 투표 생성.
        VotePlace votePlace = placeMapper.toVotePlace(memberMeeting, candidatePlace);
        votePlaceRepository.save(votePlace);
        return votePlace;
    }
}
