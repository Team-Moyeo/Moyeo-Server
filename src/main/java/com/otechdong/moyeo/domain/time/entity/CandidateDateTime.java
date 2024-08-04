package com.otechdong.moyeo.domain.time.entity;

import com.otechdong.moyeo.domain.meeting.entity.Meeting;
import com.otechdong.moyeo.domain.place.entity.Place;
import com.otechdong.moyeo.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CandidateDateTime extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "candidate_date_time_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    @Column(nullable = false)
    private Integer voteCount;
}