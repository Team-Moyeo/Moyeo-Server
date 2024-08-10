package com.otechdong.moyeo.domain.meeting.mapper;

import com.otechdong.moyeo.domain.meeting.dto.MeetingResponse;
import com.otechdong.moyeo.domain.meeting.entity.Meeting;
import com.otechdong.moyeo.domain.meeting.entity.MeetingStatus;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.memberMeeting.entity.MemberMeeting;
import com.otechdong.moyeo.domain.place.entity.Place;
import com.otechdong.moyeo.domain.voteTime.mapper.TimeMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
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
            Meeting meeting, MemberMeeting memberMeeting
    ) {
        return MeetingResponse.MeetingGetDetail.builder()
                .title(meeting.getTitle())
                .myRole(memberMeeting.getRole())
                .startDate(meeting.getStartDate())
                .endDate(meeting.getEndDate())
                .startTime(meeting.getStartTime())
                .endTime(meeting.getEndTime())
                .deadline(meeting.getDeadline())
                .numberOfPeople(meeting.getNumberOfPeople())
                .build();
    }

    public MeetingResponse.MeetingVoteConfirm toMeetingVoteConfirm(Long meetingId, List<Long> voteTimeIds, List<Long> votePlaceIds) {
        return MeetingResponse.MeetingVoteConfirm.builder()
                .meetingId(meetingId)
                .votePlaceIds(votePlaceIds)
                .voteTimeIds(voteTimeIds)
                .build();
    }

    public MeetingResponse.MeetingVoteUpdate toMeetingVoteUpdate(MeetingResponse.MeetingVoteConfirm meetingVoteConfirm) {
        return MeetingResponse.MeetingVoteUpdate.builder()
                .meetingId(meetingVoteConfirm.getMeetingId())
                .voteTimeIds(meetingVoteConfirm.getVoteTimeIds())
                .votePlaceIds(meetingVoteConfirm.getVotePlaceIds())
                .build();
    }

    public MeetingResponse.MeetingFix toFixMeeting(Meeting meeting) {
        return MeetingResponse.MeetingFix.builder()
                .meetingId(meeting.getId())
                .build();
    }

    public MeetingResponse.MeetingGetResult toMeetingGetResult(Meeting meeting) {
        return MeetingResponse.MeetingGetResult.builder()
                .title(meeting.getTitle())
                .fixedTimes(meeting.getFixedTimes()
                        .stream()
                        .map(fixedTime -> fixedTime.format(
                                new DateTimeFormatterBuilder()
                                .appendPattern("yyyy-MM-dd HH:mm")
                                .optionalStart()
                                .appendPattern(":ss")
                                .optionalEnd()
                                .toFormatter())).toList())
                .fixedPlace(meeting.getFixedPlace() != null ? meeting.getFixedPlace().getTitle() : "")
                .build();
    }

//    public MeetingResponse.MeetingVoteUpdateWithValues toMeetingVoteUpdateWithValues(MeetingResponse.MeetingVoteConfirm meetingVoteConfirm) {
//        return MeetingResponse.MeetingVoteUpdateWithValues.builder()
//                .meetingId(meetingVoteConfirm.getMeetingId())
//                .voteTimeIds(meetingVoteConfirm.getVoteTimeIds())
//                .votePlaceIds(meetingVoteConfirm.getVotePlaceIds())
//                .build();
//    }
}