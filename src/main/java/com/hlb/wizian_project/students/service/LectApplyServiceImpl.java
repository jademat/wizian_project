package com.hlb.wizian_project.students.service;

import com.hlb.wizian_project.domain.ApplyLectDTO;
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
import java.util.stream.Collectors;

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
        // 학생 정보 조회
        Studnt studnt = studntRepository.findByStdntId(stdntId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학생을 찾을 수 없습니다."));

        // 강의 정보 조회
        LectInfo lectInfo = lectInfoRepository.findByLectNo(lectNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의를 찾을 수 없습니다."));

        //  이미 신청했는지 확인
        boolean alreadyApplied = stLectApplyRepository.existsByStudntAndLectInfo(studnt, lectInfo);
        if (alreadyApplied) {
            throw new IllegalStateException("이미 신청하신 강의입니다.");
        }

        // 수강신청 객체 생성 및 저장
        LectApply apply = LectApply.builder()
                .studnt(studnt)
                .lectInfo(lectInfo)
                .applyStatus("수강신청중")  // 기본 신청 상태를 "수강신청중"로 설정
                .applyDate(LocalDateTime.now())
                .build();

        stLectApplyRepository.save(apply);  // 수강신청 저장
    }

    @Override
    public List<LectApply>  getAppliedLecturesByStudent(String stdntId) {

        Studnt studnt = studntRepository.findByStdntId(stdntId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학생을 찾을 수 없습니다."));

//        LectInfo appliedLectures = stLectApplyRepository.findLectInfoByStdntId(studnt.);
//        String applyDate = stLectApplyRepository.findLectApplyByStdntId(studnt);

        List<LectApply>  lectApply = stLectApplyRepository.findByLectNo(7);
        return lectApply;
    }

    @Override
    public List<ApplyLectDTO> getLecturesWithApplyStatus(String stdntId) {
        Studnt studnt = studntRepository.findByStdntId(stdntId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학생을 찾을 수 없습니다."));

        List<LectApply> appliedLectures = stLectApplyRepository.findByStudnt(studnt);
        List<Integer> appliedLectureNos = appliedLectures.stream()
                .map(la -> la.getLectInfo().getLectNo())
                .collect(Collectors.toList());

        return lectInfoRepository.findAll().stream()
                .map(lect -> ApplyLectDTO.builder()
                        .lectNo(lect.getLectNo())
                        .lectNm(lect.getLectNm())
                        .instNm(lect.getInstNm())
                        .lectDesc(lect.getLectDesc())
                        .lectSchd(lect.getLectSchd())
                        .lectStart(lect.getLectStart())
                        .lectSubmit(lect.getLectSubmit())
                        .lectPers(lect.getLectPers())
                        .lectLoc(lect.getLectLoc())
                        .lectStatus(lect.getLectStatus())
                        .studtLimit(lect.getStudtLimit())
                        .lectPrice(lect.getLectPrice())
                        .applied(appliedLectureNos.contains(lect.getLectNo()))  // ✅ 체크
                        .build()
                )
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public void cancelLecture(String stdntId, int lectNo) {
        // 학생 정보 조회
        Studnt studnt = studntRepository.findByStdntId(stdntId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학생을 찾을 수 없습니다."));

        // 강의 정보 조회
        LectInfo lectInfo = lectInfoRepository.findByLectNo(lectNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의를 찾을 수 없습니다."));

        // 학생이 해당 강의를 신청했는지 확인
        // Optional을 제대로 처리하도록 수정
        LectApply lectApply = stLectApplyRepository.findByStudntAndLectInfo(studnt, lectInfo)
                .orElseThrow(() -> new IllegalArgumentException("해당 강의에 대한 수강신청 기록이 없습니다."));

        // 수강신청 상태가 '수강신청중'이어야만 취소 가능
        if (!"수강신청중".equals(lectApply.getApplyStatus())) {
            throw new IllegalStateException("수강신청 상태가 아니므로 취소할 수 없습니다.");
        }

        // 수강신청 취소 (삭제)
        stLectApplyRepository.deleteByStudntAndLectInfo(studnt, lectInfo);
    }


}
