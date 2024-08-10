package com.otechdong.moyeo.domain.time.service;

import com.otechdong.moyeo.domain.meeting.entity.Meeting;
import com.otechdong.moyeo.domain.time.entity.CandidateTime;
import com.otechdong.moyeo.domain.time.mapper.TimeMapper;
import com.otechdong.moyeo.domain.time.repository.CandidateTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateTimeServiceImpl implements CandidateTimeService {
    private final CandidateTimeRepository candidateTimeRepository;
    private final TimeMapper timeMapper;


    @Override
    @Transactional
    public List<CandidateTime> generateCandidateTimes(Meeting meeting) {
        List<CandidateTime> candidateTimes = new ArrayList<>();

        LocalDate currentDate = meeting.getStartDate();
        while (!currentDate.isAfter(meeting.getEndDate())) {
            LocalTime currentTime = meeting.getStartTime();
            while (!currentTime.isAfter(meeting.getEndTime())) {
                CandidateTime candidateTime = timeMapper.toCandidateTime(meeting, currentDate, currentTime);
                candidateTimes.add(candidateTime);

                // 시간 간격을 30분 단위로 설정
                currentTime = currentTime.plusMinutes(30);
            }
            // 다음 날짜로 이동
            currentDate = currentDate.plusDays(1);
        }

        // CandidateTime 리스트를 저장
        candidateTimeRepository.saveAll(candidateTimes);

        return candidateTimes;
    }

    @Override
    @Transactional
    public List<CandidateTime> generateCandidateTimes2(Meeting meeting) {
        List<CandidateTime> candidateTimes = new ArrayList<>();

        LocalTime currentTime = meeting.getStartTime();
        while (!currentTime.isAfter(meeting.getEndTime())) {
            LocalDate currentDate = meeting.getStartDate();
            while (!currentDate.isAfter(meeting.getEndDate())) {
                CandidateTime candidateTime = timeMapper.toCandidateTime(meeting, currentDate, currentTime);
                candidateTimes.add(candidateTime);

                // 다음 날짜로 이동
                currentDate = currentDate.plusDays(1);
            }
            // 다음 시간대 30분 추가
            currentTime = currentTime.plusMinutes(30);
        }

        // CandidateTime 리스트를 저장
        candidateTimeRepository.saveAll(candidateTimes);

        return candidateTimes;
    }
}
