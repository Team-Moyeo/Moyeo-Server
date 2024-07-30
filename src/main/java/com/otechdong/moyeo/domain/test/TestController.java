package com.otechdong.moyeo.domain.test;

import com.otechdong.moyeo.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/base-response")
    private BaseResponse<String> baseResponseTestFunc() {
        return BaseResponse.onSuccess("Success Response");
    }

    @GetMapping("/error-handler")
    private BaseResponse<Long> errorHandlerTestFunc(@RequestParam(name = "number") Long number) {
        return BaseResponse.onSuccess(number);
    }

}
