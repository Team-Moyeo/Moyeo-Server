package com.otechdong.moyeo.domain.candidateTime.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class CandidateTimeResponse {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CandidateTimeGetMeetingDetail {
        private List<String> myVotedTimes;
        private List<TotalCandidateTimeInfo> totalCandidateTimes;
        private Long numberOfPeople;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TotalCandidateTimeInfo {
        private String dateTime;
        private Integer voteCount;
    }

}
