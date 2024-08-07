package com.otechdong.moyeo.domain.place.mapper;

import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.place.dto.PlaceRequest;
import com.otechdong.moyeo.domain.place.dto.PlaceResponse;
import com.otechdong.moyeo.domain.place.entity.Place;
import org.springframework.stereotype.Component;

@Component
public class PlaceMapper {

    public Place toPlace(
            Member member,
            String title,
            String address,
            Double latitude,
            Double longitude) {
        return Place.builder()
                .member(member)
                .title(title)
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

    public PlaceResponse.PlaceCreate toPlaceCreate(Place place) {
        return PlaceResponse.PlaceCreate.builder()
                .placeId(place.getId())
                .build();
    }
}
