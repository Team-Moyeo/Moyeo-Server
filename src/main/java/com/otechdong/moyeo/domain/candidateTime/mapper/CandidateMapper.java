package com.otechdong.moyeo.domain.candidateTime.mapper;

import com.otechdong.moyeo.domain.candidateTime.dto.CandidateTimeResponse;
import com.otechdong.moyeo.domain.candidateTime.entity.CandidateTime;
import com.otechdong.moyeo.domain.meeting.entity.Meeting;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

    public CandidateTimeResponse.CandidateTimeGetMeetingDetail toCandidateTimeGetMeetingDetail(
            List<String> myVotedTimes,
            List<CandidateTimeResponse.TotalCandidateTimeInfo> totalCandidateTimeInfos,
            Long numberOfPeople
    ) {
        return CandidateTimeResponse.CandidateTimeGetMeetingDetail.builder()
                .myVotedTimes(myVotedTimes)
                .totalCandidateTimes(totalCandidateTimeInfos)
                .numberOfPeople(numberOfPeople)
                .build();
    }

    public CandidateTimeResponse.TotalCandidateTimeInfo toTotalCandidateTimeInfo(
            CandidateTime candidateTime
    ) {
        return CandidateTimeResponse.TotalCandidateTimeInfo.builder()
                .dateTime(candidateTime.getDate() + " " + candidateTime.getTime())
                .voteCount(candidateTime.getVoteCount())
                .build();
    }

}
