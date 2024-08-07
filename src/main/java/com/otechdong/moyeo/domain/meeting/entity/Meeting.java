package com.otechdong.moyeo.domain.meeting.entity;

import com.otechdong.moyeo.domain.place.entity.Place;
import com.otechdong.moyeo.domain.time.entity.CandidateTime;
import com.otechdong.moyeo.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

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
@SQLRestriction("deleted_at is null")
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

    @Builder.Default
    @CollectionTable(name = "fixed_time", joinColumns = @JoinColumn(name = "meeting_id"))
    @ElementCollection(fetch = FetchType.LAZY)
    private List<LocalDateTime> fixedTimes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place fixedPlace;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @Column(nullable = false)
    private Long numberOfPeople;

    @Column(nullable = false)
    private String inviteCode;

    public void updateFixedPlace(Place fixedPlace) {
        this.fixedPlace = fixedPlace;
    }

    public void updateFixedTime(List<LocalDateTime> fixedTimes) {
        this.fixedTimes = fixedTimes;
    }
}
