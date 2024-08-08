package com.otechdong.moyeo.domain.place.repository;

import com.otechdong.moyeo.domain.place.entity.CandidatePlace;
import com.otechdong.moyeo.domain.time.entity.CandidateTime;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CandidatePlaceRepository extends JpaRepository<CandidatePlace, Long> {

    @Query("SELECT cp FROM CandidatePlace cp WHERE cp.id IN :ids AND cp.meeting.id = :meetingId")
    List<CandidatePlace> findByMeetingIdAndIds(@Param("meetingId") Long meetingId, @Param("ids") List<Long> ids);

    List<CandidatePlace> findByMeetingId(Long meetingId);
}
