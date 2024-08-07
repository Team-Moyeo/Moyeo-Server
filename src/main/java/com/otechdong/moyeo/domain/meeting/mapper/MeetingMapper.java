package com.otechdong.moyeo.domain.meeting.mapper;

import com.otechdong.moyeo.domain.meeting.dto.MeetingResponse;
import com.otechdong.moyeo.domain.meeting.entity.Meeting;
import com.otechdong.moyeo.domain.meeting.entity.MeetingStatus;
import com.otechdong.moyeo.domain.place.entity.Place;
import com.otechdong.moyeo.domain.time.entity.CandidateTime;
import com.otechdong.moyeo.domain.time.mapper.TimeMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
public class MeetingMapper {

    private TimeMapper timeMapper;

    public MeetingResponse.MeetingCreate toMeetingCreate(Meeting meeting) {
        return MeetingResponse.MeetingCreate.builder()
                .meetingId(meeting.getId())
                .build();
    }

    // TODO : 여기 구현해야됨 ㅠㅠ...
    public Meeting toMeeting(
            String title,
            LocalDate startDate,
            LocalTime startTime,
            LocalDate endDate,
            LocalTime endTime,
            List<LocalDateTime> fixedTimes,
            List<CandidateTime> candidatePlaces,
            Place fixedPlace,
            LocalDateTime deadline) {
        return Meeting.builder()
                .title(title)
                .startDate(startDate)
                .startTime(startTime)
                .endDate(endDate)
                .endTime(endTime)
                .fixedTimes(fixedTimes)
                .fixedPlace(fixedPlace)
                .deadline(deadline)
                .numberOfPeople(1L)
                .inviteCode("ASDF")
                .meetingStatus(MeetingStatus.PENDING)
                .build();
    }
}