package com.otechdong.moyeo.global.exception.errorCode;

import com.otechdong.moyeo.global.exception.ErrorCode;
import com.otechdong.moyeo.global.exception.ErrorCodeInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CandidateTimeErrorCode implements ErrorCodeInterface {

    CANDIDATE_TIME_NOT_FOUND("CANDIDATETIME001", "CANDIDATETIME이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    ;
    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.builder()
                .code(code)
                .message(message)
                .httpStatus(httpStatus)
                .build();
    }

}
