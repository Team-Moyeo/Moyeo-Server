package com.otechdong.moyeo.domain.memberMeeting.mapper;

import com.otechdong.moyeo.domain.meeting.entity.Meeting;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.memberMeeting.entity.MemberMeeting;
import com.otechdong.moyeo.domain.member.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class MemberMeetingMapper {

    public MemberMeeting toMemberMeeting(Member member, Meeting meeting, Role role) {
        return MemberMeeting.builder()
                .member(member)
                .meeting(meeting)
                .role(role)
                .build();
    }
}
