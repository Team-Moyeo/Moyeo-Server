package com.otechdong.moyeo.domain.time.repository;

import com.otechdong.moyeo.domain.meeting.entity.Meeting;
import com.otechdong.moyeo.domain.time.entity.CandidateTime;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateTimeRepository extends JpaRepository<CandidateTime, Long> {

    Optional<CandidateTime> findByMeetingAndDateAndTime(Meeting meeting, LocalDate date, LocalTime time);

    @Query("SELECT ct FROM CandidateTime ct WHERE ct.id IN :ids AND ct.meeting.id = :meetingId")
    List<CandidateTime> findByMeetingIdAndIds(@Param("meetingId") Long meetingId, @Param("ids") List<Long> ids);

    List<CandidateTime> findByMeetingId(Long meetingId);
}
