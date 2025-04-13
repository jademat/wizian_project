package com.hlb.wizian_project.students.controller;

import com.hlb.wizian_project.common.jwt.JwtTokenProvider;
import com.hlb.wizian_project.domain.LectInfo;
import com.hlb.wizian_project.domain.Studnt;
import com.hlb.wizian_project.students.service.LectApplyService;
import com.hlb.wizian_project.students.repository.StudntRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/lect")
public class LectApplyController {

    private final LectApplyService lectApplyService;
    private final JwtTokenProvider jwtTokenProvider;
    private final StudntRepository studntRepository;

    @GetMapping("/check")
    public List<LectInfo> getLectures() {
        return lectApplyService.getLectures();  // 강의 목록 조회
    }

    @PostMapping("/apply/{lectNo}")
    public ResponseEntity<?> applyForLecture(
            @RequestHeader("Authorization") String token,
            @PathVariable int lectNo // URL 경로에서 강의 ID를 받음
    ) {
        try {
            // 1. accessToken에서 학생 ID 추출
            String accessToken = token.replace("Bearer ", "");
            log.info("엑세스 토큰: {}", accessToken);

            String stdntId = jwtTokenProvider.extractUsername(accessToken);
            log.info("추출된 학생 ID: {}", stdntId);

            // 2. 학생 ID로 DB에서 학생 정보 조회
            Studnt studnt = studntRepository.findByStdntId(stdntId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 학생을 찾을 수 없습니다."));

            // 3. 수강 신청 처리
            lectApplyService.applyForLecture(stdntId, lectNo);  // 수정된 부분

            return ResponseEntity.ok("수강신청이 완료되었습니다.");
        } catch (Exception e) {
            log.error("수강신청 처리 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("수강신청에 실패했습니다.");
        }
    }



}
