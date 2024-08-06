package com.otechdong.moyeo.domain.place.controller;

import com.otechdong.moyeo.config.AuthenticationMember;
import com.otechdong.moyeo.domain.member.dto.MemberResponse;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.place.dto.PlaceRequest;
import com.otechdong.moyeo.domain.place.dto.PlaceResponse;
import com.otechdong.moyeo.domain.place.service.PlaceService;
import com.otechdong.moyeo.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/places")
public class PlaceController {

    private final PlaceService placeService;

    @PostMapping
    public BaseResponse<PlaceResponse.PlaceCreate> createPlace(
            @AuthenticationMember Member member,
            @RequestBody PlaceRequest.PlaceCreate request) {
        return BaseResponse.onSuccess(placeService.createPlace(member, request.getTitle(), request.getAddress(), request.getLatitude(), request.getLongitude()));
    }
}
