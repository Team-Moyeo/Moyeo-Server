package com.otechdong.moyeo.domain.candidatePlace.dto;

import com.otechdong.moyeo.domain.candidateTime.dto.CandidateTimeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class CandidatePlaceResponse {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CandidatePlaceGetMeetingDetail {
        private List<String> myVotedPlaces;
        private List<TotalCandidatePlaceInfo> totalCandidatePlaces;
        private Long numberOfPeople;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TotalCandidatePlaceInfo {
        private String title;
        private String address;
        private Double latitude;
        private Double longitude;
        private Integer voteCount;
    }
}
