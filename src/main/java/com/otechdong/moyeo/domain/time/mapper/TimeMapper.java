package com.otechdong.moyeo.domain.time.mapper;

import com.otechdong.moyeo.domain.meeting.dto.MeetingRequest;
import com.otechdong.moyeo.domain.meeting.entity.Meeting;
import com.otechdong.moyeo.domain.memberMeeting.entity.MemberMeeting;
import com.otechdong.moyeo.domain.time.entity.CandidateTime;
import com.otechdong.moyeo.domain.time.entity.VoteTime;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class TimeMapper {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public LocalDateTime toLocalDateTime(
            String date,
            String time) {
        if (date == null || time == null) {
            throw new IllegalArgumentException("Date and time must not be null");
        }

        LocalDateTime localDateTime = LocalDateTime.parse(date + "T" + time);

        return localDateTime;
    }

    public CandidateTime toCandidateTime(
            Meeting meeting,
            LocalDate date,
            LocalTime time
    ) {
        return CandidateTime.builder()
                .meeting(meeting)
                .date(date)
                .time(time)
                .voteCount(0)
                .build();
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
