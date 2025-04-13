package com.hlb.wizian_project.students.service;

import com.hlb.wizian_project.domain.LectApply;
import com.hlb.wizian_project.domain.LectInfo;
import com.hlb.wizian_project.domain.Studnt;
import com.hlb.wizian_project.instructors.repository.LectInfoRepository;
import com.hlb.wizian_project.students.repository.StLectApplyRepository;
import com.hlb.wizian_project.students.repository.StudntRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectApplyServiceImpl implements LectApplyService {

    private final LectInfoRepository lectInfoRepository;
    private final StLectApplyRepository stLectApplyRepository;
    private final StudntRepository studntRepository;

    @Override
    public List<LectInfo> getLectures() {
        return lectInfoRepository.findAll();  // 강의 목록 조회
    }

    @Override
    @Transactional
    public void applyForLecture(String stdntId, int lectNo) {
        // 1. 학생 정보 조회
        Studnt studnt = studntRepository.findByStdntId(stdntId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학생을 찾을 수 없습니다."));

        // 2. 강의 정보 조회
        LectInfo lectInfo = lectInfoRepository.findById((long) lectNo)  // 수정된 부분
                .orElseThrow(() -> new IllegalArgumentException("해당 강의를 찾을 수 없습니다."));

        // 3. 이미 신청했는지 체크
        boolean alreadyApplied = stLectApplyRepository
                .findByStudntAndLectInfo(studnt, lectInfo)
                .isPresent();

        if (alreadyApplied) {
            throw new IllegalStateException("이미 신청한 강의입니다.");
        }

        // 4. 수강신청 객체 생성 및 저장
        LectApply apply = LectApply.builder()
                .studnt(studnt)
                .lectInfo(lectInfo)
                .applyStatus("수강신청중")  // 기본 신청 상태를 "수강신청중"로 설정
                .applyDate(LocalDateTime.now())
                .build();

        stLectApplyRepository.save(apply);  // 수강신청 저장
    }


}
