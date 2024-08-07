package com.otechdong.moyeo.domain.memberMeeting.service;

import com.otechdong.moyeo.domain.meeting.entity.Meeting;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.member.entity.Role;
import com.otechdong.moyeo.domain.memberMeeting.entity.MemberMeeting;

public interface MemberMeetingService {

    MemberMeeting createMemberMeeting(Member member, Meeting meeting, Role role);

}
