package com.otechdong.moyeo.domain.meeting.entity;

import com.otechdong.moyeo.domain.place.entity.Place;
import com.otechdong.moyeo.domain.time.entity.CandidateTime;
import com.otechdong.moyeo.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meeting extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private MeetingStatus meetingStatus;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column
    private LocalTime startTime;

    @Column
    private LocalTime endTime;

    @OneToMany(mappedBy = "meeting", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<CandidateTime> fixedTimeDates = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place fixedPlace;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @Column(nullable = false)
    private Long numberOfPeople;

    @Column(nullable = false)
    private String inviteCode;

    public void updateFixedPlace(Place place) {
        this.fixedPlace = place;
    }
}
