package com.otechdong.moyeo.domain.meeting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class MeetingRequest {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MeetingCreate {
        private String title;
        private LocalDate startDate;
        private LocalDate endDate;
        private LocalTime startTime;
        private LocalTime endTime;
        private List<LocalDateTime> fixedTimes;
        private MeetingCreatePlace fixedPlace;
        private List<MeetingCreatePlace> candidatePlaces;
        private LocalDateTime deadline;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MeetingCreateTime {
        private String date;
        private String time;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MeetingCreatePlace {
        private String title;
        private String address;
        private Double latitude;
        private Double longitude;
    }


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MeetingVoteConfirm {
        private List<Long> candidateTimeIds;
        private List<Long> candidatePlaceIds;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MeetingVoteUpdate {
        private List<Long> candidateTimeIds;
        private List<Long> candidatePlaceIds;
    }
}
