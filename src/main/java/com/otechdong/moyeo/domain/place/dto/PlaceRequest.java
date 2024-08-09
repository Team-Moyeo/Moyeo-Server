package com.otechdong.moyeo.domain.place.dto;

import com.otechdong.moyeo.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PlaceRequest {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PlaceCreate {
        private String title;
        private String address;
        private Double latitude;
        private Double longitude;
    }
}
