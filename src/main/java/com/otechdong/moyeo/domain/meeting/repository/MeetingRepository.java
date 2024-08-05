package com.otechdong.moyeo.domain.meeting.repository;

import com.otechdong.moyeo.domain.meeting.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Long, Meeting> {

}
