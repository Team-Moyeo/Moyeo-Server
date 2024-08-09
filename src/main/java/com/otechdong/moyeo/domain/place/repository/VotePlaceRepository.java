package com.otechdong.moyeo.domain.place.repository;

import com.otechdong.moyeo.domain.memberMeeting.entity.MemberMeeting;
import com.otechdong.moyeo.domain.place.entity.CandidatePlace;
import com.otechdong.moyeo.domain.place.entity.VotePlace;
import com.otechdong.moyeo.domain.time.entity.CandidateTime;
import com.otechdong.moyeo.domain.time.entity.VoteTime;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VotePlaceRepository extends JpaRepository<VotePlace, Long> {
    Optional<VotePlace> findByMemberMeetingAndCandidatePlace(MemberMeeting memberMeeting, CandidatePlace candidatePlace);

    @Query("SELECT vp FROM VotePlace vp WHERE vp.memberMeeting = :memberMeeting AND vp.id IN :ids")
    List<VotePlace> findByMemberMeetingAndIds(@Param("memberMeeting") MemberMeeting memberMeeting, @Param("ids") List<Long> ids);

    List<VotePlace> findByMemberMeeting(MemberMeeting memberMeeting);
}
