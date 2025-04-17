package com.hlb.wizian_project.students.controller;

import com.hlb.wizian_project.common.jwt.JwtTokenProvider;
import com.hlb.wizian_project.domain.ApplyLectDTO;
import com.hlb.wizian_project.domain.LectApply;
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
    public ResponseEntity<?> getLectures(@RequestHeader("Authorization") String token) {
        try {
            String accessToken = token.replace("Bearer ", "");
            String stdntId = jwtTokenProvider.extractUsername(accessToken);

            List<ApplyLectDTO> result = lectApplyService.getLecturesWithApplyStatus(stdntId);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("강의 목록 조회 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("강의 목록을 가져오지 못했습니다.");
        }
    }

    @PostMapping("/apply/{lectNo}")
    public ResponseEntity<?> applyForLecture(
            @RequestHeader("Authorization") String token,
            @PathVariable int lectNo // URL 경로에서 강의 ID를 받음
    ) {

        log.info(">> applyForLecture {} ", lectNo);
        log.info(">> applyForLecture {} ", token);

        try {
            // 1. accessToken에서 학생 ID 추출
            String accessToken = token.replace("Bearer ", "");
            log.info("엑세스 토큰: {}", accessToken);

            String stdntId = jwtTokenProvider.extractUsername(accessToken);
            log.info("추출된 학생 ID: {}", stdntId);

            // 2. 학생 ID로 DB에서 학생 정보 조회
            Studnt studnt = studntRepository.findByStdntId(stdntId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 학생을 찾을 수 없습니다."));

            log.info(">> studnt email: {}", studnt.getStdntEmail());

            // 3. 수강 신청 처리
            lectApplyService.applyForLecture(stdntId, lectNo);  // 수정된 부분

            return ResponseEntity.ok("수강신청이 완료되었습니다.");

        } catch (IllegalStateException e) {
            // 이미 신청한 강의일 경우
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

        } catch (IllegalArgumentException e) {
            // 학생/강의 정보가 없는 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (Exception e) {
            // 그 외
            log.error("수강신청 처리 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("수강신청 처리 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/mine")
    public ResponseEntity<?> getMyAppliedLectures(@RequestHeader("Authorization") String token) {
        try {
            String accessToken = token.replace("Bearer ", "");
            String stdntId = jwtTokenProvider.extractUsername(accessToken);
            List<LectApply>  lectApply = lectApplyService.getAppliedLecturesByStudent(stdntId);


            log.info(">> lectApply: {}", lectApply);

            return ResponseEntity.ok(lectApply);

        } catch (Exception e) {
            log.error("수강 신청 내역 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("내역 조회 중 오류 발생");
        }
    }

    @DeleteMapping("/cancel/{lectNo}")
    public ResponseEntity<?> cancelLecture(@RequestHeader("Authorization") String token,
                                           @PathVariable int lectNo) {
        try {
            // 토큰에서 학생 ID 추출
            String accessToken = token.replace("Bearer ", "");
            String stdntId = jwtTokenProvider.extractUsername(accessToken);

            // 수강 취소 처리
            lectApplyService.cancelLecture(stdntId, lectNo);

            return ResponseEntity.ok("수강 취소가 완료되었습니다.");
        } catch (IllegalStateException e) {
            // 상태가 '수강신청중'이 아닌 경우
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            // 학생이나 강의 정보가 없는 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            log.error("수강취소 처리 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("수강 취소 처리 중 오류가 발생했습니다.");
        }
    }


}
