package com.otechdong.moyeo.global.exception.errorCode;

import com.otechdong.moyeo.global.exception.ErrorCode;
import com.otechdong.moyeo.global.exception.ErrorCodeInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PlaceErrorCode implements ErrorCodeInterface {
    PLACE_NOT_FOUND("PLACE001", "존재하지 않는 장소입니다.", HttpStatus.UNAUTHORIZED)
    ,
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
