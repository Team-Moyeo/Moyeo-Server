package com.otechdong.moyeo.domain.time.repository;

import com.otechdong.moyeo.domain.memberMeeting.entity.MemberMeeting;
import com.otechdong.moyeo.domain.time.entity.CandidateTime;
import com.otechdong.moyeo.domain.time.entity.VoteTime;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteTimeRepository extends JpaRepository<VoteTime, Long> {

    Optional<VoteTime> findByMemberMeetingAndCandidateTime(MemberMeeting memberMeeting, CandidateTime candidateTime);

//    @Query("SELECT vt FROM VoteTime vt LEFT JOIN vt.candidateTime ct WHERE ct.id IN :candidateTimeIds AND ct.meeting.id = :meetingId")
//    List<VoteTime> findByMeetingIdsAndCandidateTimeIds(@Param("meetingId") Long meetingId, @Param("candidateTimeIds") List<Long> candidateTimeIds);
}
