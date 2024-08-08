package com.otechdong.moyeo.domain.time.entity;

import com.otechdong.moyeo.domain.memberMeeting.entity.MemberMeeting;
import com.otechdong.moyeo.domain.place.entity.Place;
import com.otechdong.moyeo.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoteTime extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_time_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_meeting_id")
    private MemberMeeting memberMeeting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_time_id")
    private CandidateTime candidateTime;

//    @Column(nullable = false)
//    private Boolean isVoted;
//
//    public void updateIsVoted(Boolean isVoted) {
//        this.isVoted = isVoted;
//    }

}