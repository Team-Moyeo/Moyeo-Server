package com.otechdong.moyeo.domain.place.service;

import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.place.dto.PlaceRequest;
import com.otechdong.moyeo.domain.place.dto.PlaceResponse;
import com.otechdong.moyeo.domain.place.entity.Place;

public interface PlaceService {

    Place findPlace(Long placeId);

    PlaceResponse.PlaceCreate createPlace(
            Member member,
            String title,
            String address,
            Double latitude,
            Double longitude
    );


    PlaceResponse.PlaceGet getPlace(
            Long placeId
    );
}
