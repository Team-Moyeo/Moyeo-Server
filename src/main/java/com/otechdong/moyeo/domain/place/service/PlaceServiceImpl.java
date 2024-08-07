package com.otechdong.moyeo.domain.place.service;

import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.place.dto.PlaceRequest;
import com.otechdong.moyeo.domain.place.dto.PlaceResponse;
import com.otechdong.moyeo.domain.place.entity.Place;
import com.otechdong.moyeo.domain.place.mapper.PlaceMapper;
import com.otechdong.moyeo.domain.place.repository.PlaceRepository;
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
}
