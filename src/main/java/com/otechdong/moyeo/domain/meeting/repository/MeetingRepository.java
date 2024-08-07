package com.otechdong.moyeo.domain.meeting.repository;

import com.otechdong.moyeo.domain.meeting.entity.Meeting;
import com.otechdong.moyeo.domain.meeting.entity.MeetingStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    @Query("SELECT m FROM Meeting m LEFT JOIN MemberMeeting mm ON m.id = mm.meeting.id WHERE mm.member.id = :memberId")
    List<Meeting> findMeetingsByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT m FROM Meeting m LEFT JOIN MemberMeeting mm ON m.id = mm.meeting.id WHERE mm.member.id = :memberId AND m.meetingStatus = :meetingStatus")
    List<Meeting> findMeetingsByMemberIdAndMeetingStatus(@Param("memberId") Long memberId, @Param("meetingStatus") MeetingStatus meetingStatus);

    Optional<Meeting> findByInviteCode(String inviteCode);

    boolean existsByInviteCode(String inviteCode);
}
