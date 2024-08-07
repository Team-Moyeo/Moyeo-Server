package com.otechdong.moyeo.domain.meeting.mapper;

import com.otechdong.moyeo.domain.meeting.dto.MeetingResponse;
import com.otechdong.moyeo.domain.meeting.entity.Meeting;
import com.otechdong.moyeo.domain.meeting.entity.MeetingStatus;
import com.otechdong.moyeo.domain.place.entity.CandidatePlace;
import com.otechdong.moyeo.domain.place.entity.Place;
import com.otechdong.moyeo.domain.time.entity.CandidateTime;
import com.otechdong.moyeo.domain.time.mapper.TimeMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

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
            Place fixedPlace,
            LocalDateTime deadline,
            String inviteCode) {
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
                .inviteCode(inviteCode)
                .meetingStatus(MeetingStatus.PENDING)
                .build();
    }

    public MeetingResponse.MeetingGetList toMeetingGetList(List<MeetingResponse.MeetingGetListMeetingInfo> meetingList) {
        return MeetingResponse.MeetingGetList.builder()
                .meetingList(meetingList)
                .build();
    }
    public MeetingResponse.MeetingGetListMeetingInfo toMeetingGetListMeetingInfo(
            Meeting meeting
    ) {
        return MeetingResponse.MeetingGetListMeetingInfo.builder()
                .meetingId(meeting.getId())
                .title(meeting.getTitle())
                .deadline(meeting.getDeadline())
                .meetingStatus(meeting.getMeetingStatus())
                .build();
    }

    public MeetingResponse.MeetingDelete toMeetingDelete(
            Meeting meeting
    ) {
       return MeetingResponse.MeetingDelete.builder()
               .meetingId(meeting.getId())
               .build();
    }


    public MeetingResponse.MeetingJoinWithInviteCode toMeetingJoinWithInviteCode(
            Meeting meeting
    ) {
        return MeetingResponse.MeetingJoinWithInviteCode.builder()
                .meetingId(meeting.getId())
                .build();
    }

    public MeetingResponse.MeetingGetInviteCode toMeetingGetInviteCode(String inviteCode) {
        return MeetingResponse.MeetingGetInviteCode.builder()
                .inviteCode(inviteCode)
                .build();
    }

    public MeetingResponse.MeetingGetDetail toMeetingGetDetail(
            Meeting meeting,
            List<LocalDateTime> myCandidateTimes,
            List<Double> totalTimeTable,
            List<MeetingResponse.MeetingGetDetailCandidatePlace> candidatePlaces
    ) {
        return MeetingResponse.MeetingGetDetail.builder()
                .title(meeting.getTitle())
                .startDate(meeting.getStartDate())
                .endDate(meeting.getEndDate())
                .startTime(meeting.getStartTime())
                .endTime(meeting.getEndTime())
                .deadline(meeting.getDeadline())
                .myCandidateTimes(myCandidateTimes)
                .totalTimeTable(totalTimeTable)
                .candidatePlaces(candidatePlaces)
                .numberOfPeople(meeting.getNumberOfPeople())
                .build();
    }
}