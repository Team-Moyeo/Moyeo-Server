package com.otechdong.moyeo.global.exception.errorCode;

import com.otechdong.moyeo.global.exception.ErrorCode;
import com.otechdong.moyeo.global.exception.ErrorCodeInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CandidatePlaceErrorCode implements ErrorCodeInterface {
    CANDIDATE_PLACE_NOT_FOUND("CANDIDATEPLACE001", "CANDIDATEPLACE가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    CANDIDATE_PLACE_ALREADY_EXIST("CANDIDATEPLACE002", "CANDIDATEPLACE가 이미 존재합니다.", HttpStatus.BAD_REQUEST),
    CANDIDATE_PLACE_PERMISSION_DENIED("CANDIDATEPLACE002", "권한이 없습니다.", HttpStatus.FORBIDDEN),
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
