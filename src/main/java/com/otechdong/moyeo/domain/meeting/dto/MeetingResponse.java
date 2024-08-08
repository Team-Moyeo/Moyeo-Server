package com.otechdong.moyeo.domain.meeting.dto;

import com.otechdong.moyeo.domain.meeting.entity.MeetingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class MeetingResponse {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MeetingCreate {
        private Long meetingId;
    }


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MeetingAddCandidatePlace {
        private Long candidatePlaceId;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MeetingGetList {
        private List<MeetingGetListMeetingInfo> meetingList;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MeetingGetListMeetingInfo {
        private Long meetingId;
        private String title;
        private LocalDateTime deadline;
        private MeetingStatus meetingStatus;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MeetingDelete {
        private Long meetingId;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MeetingJoinWithInviteCode {
        private Long meetingId;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MeetingGetInviteCode {
        private String inviteCode;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MeetingGetDetail {
        private String title;
        private LocalDate startDate;
        private LocalDate endDate;
        private LocalTime startTime;
        private LocalTime endTime;
        private LocalDateTime deadline;
        private List<LocalDateTime> myCandidateTimes;
        private List<Double> totalTimeTable;
        private List<MeetingGetDetailCandidatePlace> candidatePlaces;
        private Long numberOfPeople;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MeetingGetDetailCandidatePlace {
        private String title;
        private String address;
        private Double latitude;
        private Double longitude;
        private Integer voteCount;
    }


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MeetingVoteConfirm {
        private List<Long> votePlaceIds;
        private List<Long> voteTimeIds;
    }
}
