package com.otechdong.moyeo.domain.voteTime.mapper;

import com.otechdong.moyeo.domain.memberMeeting.entity.MemberMeeting;
import com.otechdong.moyeo.domain.candidateTime.entity.CandidateTime;
import com.otechdong.moyeo.domain.voteTime.entity.VoteTime;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@Component
public class TimeMapper {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd HH:mm")
            .optionalStart()
            .appendPattern(":ss")
            .optionalEnd()
            .toFormatter();

    public LocalDateTime toLocalDateTime(
            String date,
            String time) {
        if (date == null || time == null) {
            throw new IllegalArgumentException("Date and time must not be null");
        }

        LocalDateTime localDateTime = LocalDateTime.parse(date + " " + time, DATE_TIME_FORMATTER);

        return localDateTime;
    }

    public String toLocalDateTimeString(
            LocalDate date,
            LocalTime time) {
        if (date == null || time == null) {
            throw new IllegalArgumentException("Date and time must not be null");
        }

        return date.toString() + " " + time.toString();
    }


    public VoteTime toVoteTime(
            MemberMeeting memberMeeting,
            CandidateTime candidateTime
    ) {
        return VoteTime.builder()
                .memberMeeting(memberMeeting)
                .candidateTime(candidateTime)
                .build();
    }

    // TODO : 이 부분 구현하기
//    public List<CandidateTime> toCandidateTimes(List<LocalDateTime> localDateTimes) {
//        return
//    }

//    public CandidateTime toCandidateTime(String date, String time) {
//        return CandidateTime.builder()
//                .meeting()
//                .date()
//                .time()
//                .voteCount()
//                .build();
//    }
}
