package com.otechdong.moyeo.domain.candidatePlace.repository;

import com.otechdong.moyeo.domain.meeting.entity.Meeting;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.candidatePlace.entity.CandidatePlace;
import com.otechdong.moyeo.domain.place.entity.Place;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CandidatePlaceRepository extends JpaRepository<CandidatePlace, Long> {

    @Query("SELECT cp FROM CandidatePlace cp WHERE cp.id IN :ids AND cp.meeting.id = :meetingId")
    List<CandidatePlace> findByMeetingIdAndIds(@Param("meetingId") Long meetingId, @Param("ids") List<Long> ids);

    @Query("SELECT cp FROM CandidatePlace cp WHERE cp.place.title IN :values AND cp.meeting.id = :meetingId")
    List<CandidatePlace> findByMeetingIdAndValues(@Param("meetingId") Long meetingId, @Param("ids") List<String> values);

    List<CandidatePlace> findByMeetingId(Long meetingId);

    Optional<CandidatePlace> findByMeetingAndPlace(Meeting meeting, Place place);

    Boolean existsByMeetingAndPlace(Meeting meeting, Place place);

    Optional<CandidatePlace> findByMeetingAndMember(Meeting meeting, Member member);

    Optional<CandidatePlace> findByMeetingIdAndId(Long meetingId, Long candidatePlaceId);
}
