package com.otechdong.moyeo.domain.place.controller;

import com.otechdong.moyeo.config.AuthenticationMember;
import com.otechdong.moyeo.domain.member.dto.MemberResponse;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.place.dto.PlaceRequest;
import com.otechdong.moyeo.domain.place.dto.PlaceResponse;
import com.otechdong.moyeo.domain.place.service.PlaceService;
import com.otechdong.moyeo.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/places")
@Tag(name = "장소 API", description = "장소 API 입니다.")
public class PlaceController {

    private final PlaceService placeService;

    @Operation(summary = "장소 등록 API", description = "장소 등록 API 입니다.")
    @PostMapping
    public BaseResponse<PlaceResponse.PlaceCreate> createPlace(
            @AuthenticationMember Member member,
            @RequestBody PlaceRequest.PlaceCreate request) {
        return BaseResponse.onSuccess(placeService.createPlace(member, request.getTitle(), request.getAddress(), request.getLatitude(), request.getLongitude()));
    }
}
