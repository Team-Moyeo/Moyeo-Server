package com.otechdong.moyeo.domain.time.repository;

import com.otechdong.moyeo.domain.meeting.entity.Meeting;
import com.otechdong.moyeo.domain.time.entity.CandidateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface CandidateTimeRepository extends JpaRepository<CandidateTime, Long> {

    Optional<CandidateTime> findByMeetingAndDateAndTime(Meeting meeting, LocalDate date, LocalTime time);
}
