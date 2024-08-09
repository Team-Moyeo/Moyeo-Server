package com.otechdong.moyeo.domain.test.controller;

import com.otechdong.moyeo.config.AuthenticationMember;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequiredArgsConstructor
public class TestController {

    @Value("${server.env}")
    private String env;
    @Value("${server.port}")
    private String serverPort;
    @Value("${server.serverAddress}")
    private String serverAddress;
    @Value("${serverName}")
    private String serverName;

    @GetMapping("/base-response")
    private BaseResponse<String> baseResponseTestFunc() {
        return BaseResponse.onSuccess("Success Response");
    }

    @GetMapping("/error-handler")
    private BaseResponse<Long> errorHandlerTestFunc(@RequestParam(name = "number") Long number) {
        return BaseResponse.onSuccess(number);
    }

    @GetMapping("/health-check")
    private BaseResponse<?> healthCheck() {
        Map<String, String> responseData = new TreeMap<>();
        responseData.put("serverName", serverName);
        responseData.put("serverAddress", serverAddress);
        responseData.put("serverPort", serverPort);
        responseData.put("env", env);
        return BaseResponse.onSuccess(responseData);
    }

    @GetMapping("/env")
    private String getEnv() {
        Map<String, String> responseData = new HashMap<>();

        return env;
    }

    @GetMapping("/test")
    private String getClientId(
            @AuthenticationMember Member member
    ) {
        System.out.println(member);
        return "Good";
    }

}
