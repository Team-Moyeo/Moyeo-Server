package com.otechdong.moyeo.domain.place.entity;

import com.otechdong.moyeo.domain.memberMeeting.entity.MemberMeeting;
import com.otechdong.moyeo.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is null")
public class VotePlace extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_place_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_meeting_id")
    private MemberMeeting memberMeeting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_place_id")
    private CandidatePlace candidatePlace;

//    @Column(nullable = false)
//    private Boolean isVoted;
}
