package com.otechdong.moyeo.domain.place.service;

import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.place.dto.PlaceResponse;
import com.otechdong.moyeo.domain.place.entity.Place;
import com.otechdong.moyeo.domain.place.mapper.PlaceMapper;
import com.otechdong.moyeo.domain.place.repository.PlaceRepository;
import com.otechdong.moyeo.global.exception.RestApiException;
import com.otechdong.moyeo.global.exception.errorCode.PlaceErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceMapper placeMapper;

    @Override
    public Place findPlace(Long placeId) {
        return placeRepository.findById(placeId).orElseThrow(() -> new RestApiException(PlaceErrorCode.PLACE_NOT_FOUND));
    }

    @Override
    public PlaceResponse.PlaceCreate createPlace(
            Member member,
            String title,
            String address,
            Double latitude,
            Double longitude
    ) {
        Optional<Place> optionalPlace = placeRepository.findByTitleAndAddressAndLatitudeAndLongitude(title, address, latitude, longitude);

        // 1. 이미 유저가 등록한 장소이면 해당 placeId 반환
        if (!optionalPlace.isEmpty()) {
            Place place = optionalPlace.get();
            return placeMapper.toPlaceCreate(place);
        }
        Place newPlace = placeMapper.toPlace(member, title, address, latitude, longitude);
        placeRepository.save(newPlace);
        return placeMapper.toPlaceCreate(newPlace);
    }

    @Override
    public PlaceResponse.PlaceGet getPlace(Long placeId) {
        Place place = findPlace(placeId);
        return placeMapper.toPlaceGet(place);
    }
}
