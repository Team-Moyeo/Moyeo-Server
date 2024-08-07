package com.otechdong.moyeo.global.exception.errorCode;

import com.otechdong.moyeo.global.exception.ErrorCode;
import com.otechdong.moyeo.global.exception.ErrorCodeInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberMeetingErrorCode implements ErrorCodeInterface {
    MEMBER_MEETING_NOT_FOUND("MEMBERMEETING001", "모임에 멤버가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    MEMBER_MEETING_ALREADY_EXIST("MEMBERMEETING002", "이미 가입된 모임입니다.", HttpStatus.BAD_REQUEST),
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
