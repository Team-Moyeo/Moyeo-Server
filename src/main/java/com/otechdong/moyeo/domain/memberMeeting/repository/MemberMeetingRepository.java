package com.otechdong.moyeo.domain.memberMeeting.repository;

import com.otechdong.moyeo.domain.meeting.entity.Meeting;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.memberMeeting.entity.MemberMeeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberMeetingRepository extends JpaRepository<MemberMeeting, Long> {

    Optional<MemberMeeting> findByMemberAndMeeting(Member member, Meeting meeting);
    boolean existsByMemberAndMeeting(Member member, Meeting meeting);
}
