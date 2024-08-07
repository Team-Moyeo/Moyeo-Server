package com.otechdong.moyeo.domain.member.repository;

import com.otechdong.moyeo.domain.member.entity.Member;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Boolean existsByClientId(String clientId);

    Optional<Member> findByClientId(String clientId);

    @Query("SELECT m FROM Member m LEFT JOIN MemberMeeting mm ON m.id = mm.member.id WHERE mm.meeting.id = :meetingId")
    Optional<List<Member>> findByMeeting(@Param("meetingId") Long meetingId);
}
