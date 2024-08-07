package com.otechdong.moyeo.domain.memberMeeting.service;

import com.otechdong.moyeo.domain.meeting.entity.Meeting;
import com.otechdong.moyeo.domain.member.entity.Member;
import com.otechdong.moyeo.domain.member.entity.Role;
import com.otechdong.moyeo.domain.memberMeeting.entity.MemberMeeting;
import com.otechdong.moyeo.domain.memberMeeting.mapper.MemberMeetingMapper;
import com.otechdong.moyeo.domain.memberMeeting.repository.MemberMeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberMeetingServiceImpl implements MemberMeetingService {

    private final MemberMeetingRepository memberMeetingRepository;
    private final MemberMeetingMapper memberMeetingMapper;

    @Override
    public MemberMeeting createMemberMeeting(Member member, Meeting meeting, Role role) {
        Optional<MemberMeeting> optionalMemberMeeting = memberMeetingRepository.findByMemberAndMeeting(member, meeting);
        if (!optionalMemberMeeting.isEmpty()) {
            return optionalMemberMeeting.get();
        }
        MemberMeeting memberMeeting = memberMeetingMapper.toMemberMeeting(member, meeting, role);
        memberMeetingRepository.save(memberMeeting);
        return memberMeeting;
    }
}
