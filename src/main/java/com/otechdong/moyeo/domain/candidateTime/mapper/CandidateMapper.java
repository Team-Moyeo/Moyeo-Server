package com.otechdong.moyeo.domain.candidateTime.mapper;

import com.otechdong.moyeo.domain.candidateTime.entity.CandidateTime;
import com.otechdong.moyeo.domain.meeting.entity.Meeting;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class CandidateMapper {

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
}
