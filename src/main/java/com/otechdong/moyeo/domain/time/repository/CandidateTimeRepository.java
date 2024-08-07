package com.otechdong.moyeo.domain.time.repository;

import com.otechdong.moyeo.domain.time.entity.CandidateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateTimeRepository extends JpaRepository<CandidateTime, Long> {
}
