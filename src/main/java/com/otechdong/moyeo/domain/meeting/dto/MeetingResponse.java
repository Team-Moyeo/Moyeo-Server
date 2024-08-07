package com.otechdong.moyeo.domain.meeting.dto;

import com.otechdong.moyeo.domain.meeting.entity.MeetingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
}
