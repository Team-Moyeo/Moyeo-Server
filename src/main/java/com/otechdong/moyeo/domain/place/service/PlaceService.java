package com.otechdong.moyeo.domain.place.service;

import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.place.dto.PlaceRequest;
import com.otechdong.moyeo.domain.place.dto.PlaceResponse;

public interface PlaceService {

    PlaceResponse.PlaceCreate createPlace(
            Member member,
            String title,
            String address,
            Double latitude,
            Double longitude
    );
}
