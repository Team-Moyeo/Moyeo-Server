package com.otechdong.moyeo.domain.member.repository;

import com.otechdong.moyeo.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Boolean existsByClientId(String clientId);

    Optional<Member> findByClientId(String clientId);
}
