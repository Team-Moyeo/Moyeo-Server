package com.otechdong.moyeo.domain.place.repository;

import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    Optional<Place> findByTitleAndAddressAndLatitudeAndLongitude(String title, String address, Double latitude, Double longitude);

}
