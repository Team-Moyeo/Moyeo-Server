package com.otechdong.moyeo.global.exception.errorCode;

import com.otechdong.moyeo.global.exception.ErrorCode;
import com.otechdong.moyeo.global.exception.ErrorCodeInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MeetingErrorCode implements ErrorCodeInterface {
    MEETING_NOT_FOUND("MEETING001", "MEETING이 존재하지 않습니다.",HttpStatus.NOT_FOUND),
    MEETING_ACCESS_DENIED("MEETING002", "권한이 없습니다.",HttpStatus.FORBIDDEN),
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
