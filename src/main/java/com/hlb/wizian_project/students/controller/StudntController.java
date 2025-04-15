package com.hlb.wizian_project.students.controller;

import com.hlb.wizian_project.domain.Studnt;
import com.hlb.wizian_project.students.repository.StudntRepository;
import com.hlb.wizian_project.students.service.StudntService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/stdnt")
public class StudntController {

    private final StudntService studntService;
    private final StudntRepository studntRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/myinfo")
    public ResponseEntity<?> myinfo(Authentication authentication) {
        ResponseEntity<?> response = ResponseEntity.internalServerError().build();

        // 로그인 인증이 성공했다면
        if (authentication != null && authentication.isAuthenticated()) {
            // 인증 완료된 사용자 정보(아이디)를 가져옴
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            log.info("로그인 ID: " + userDetails.getUsername());

            // 실제 사용자 정보 가져오기
            try {
                Studnt user = studntService.getUserById(userDetails.getUsername());
                response = ResponseEntity.ok().body(user);
            } catch (Exception e) {
                log.info("Error fetching user data: " + e.getMessage());
                response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("사용자 정보를 불러오는 데 실패했습니다.");
            }
        } else {
            response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패!! - 로그인하세요!!");
        }

        return response;
    }

    // 이메일 인증 코드 발송
    @PostMapping("/sendVerificationEmail")
    public ResponseEntity<?> sendVerificationEmail(@RequestParam String email, @RequestParam String userId) {
        try {
            // 이메일 인증 링크 발송
            studntService.sendVerificationEmail(email, userId, null);
            return ResponseEntity.ok("이메일 인증 링크가 발송되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("이메일 발송에 실패했습니다.");
        }
    }

    @GetMapping("/verifyCode/{userid}/{email:.+}/{code}")
    public void verifyCode(@PathVariable String userid,
                           @PathVariable String email,
                           @PathVariable String code,
                           HttpServletResponse res) throws IOException {

        // 인증 상태 확인 (예시: DB에서 해당 사용자 및 코드 확인)
        String result = "fail";  // enable 기본 값은 실패로 설정 -- 이 때는 로그인 되면 안됨

        // 실제로 인증 여부 확인 로직
        boolean isVerified = checkIfVerified(userid, email, code);  // 인증 상태 확인 메서드
        //boolean isAlreadyVerified = checkIfAlreadyVerified(userid); // 이미 인증된 계정 확인 메서드

        log.info(">> {} {} {}", userid, email, code);
//        log.info(">> {} {}", isVerified, isAlreadyVerified);
        log.info(">> {} {}", isVerified);

        // 회원 정보 테이블에 enable의 값을 true로 변경
        if (isVerified) {
            // 사용자 정보를 가져옴
            Optional<Studnt> studentOptional = studntRepository.findByStdntId(userid);

            if (studentOptional.isPresent()) {
                Studnt student = studentOptional.get();
                // enable 값을 "true"로 변경
                student.setEnable("true");
                // 업데이트된 엔티티 저장
                studntRepository.save(student);  // DB에 변경된 정보 저장
                result = "ok";  // 인증 성공 시
            } else {
                log.error("사용자를 찾을 수 없습니다.");
            }
        }

        String redirectUrl = "http://localhost:3000/verifyEmail/" + result;
        res.sendRedirect(redirectUrl);
    }


    private boolean checkIfVerified(String userid, String email, String code) {
        boolean result = false;
        // DB에서 사용자 정보 가져오기
        Optional<Studnt> user = studntRepository.findByStdntIdAndStdntEmailAndVerifycode(userid, email, code);
        log.info(">> {} ", user.get().getStdntNm());

        if (user.isPresent()) {
            // 사용자 정보가 있고, 인증안되었으면 enable이 "false"여야 함
            result = user.get().getEnable().equals("false");
        }
        return result;
    }


    private boolean checkIfAlreadyVerified(String userid) {
        Optional<Studnt> user = studntRepository.findByStdntId(userid);

        // 사용자 정보가 있고, enable이 "true"라면 이미 인증된 상태
        return user.isPresent() && "true".equals(user.get().getEnable());
    }



    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody Studnt updatedStudnt, Authentication authentication) {
        // 인증된 사용자 확인
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String loginId = userDetails.getUsername();

            try {
                studntService.updateStdntInfo(loginId, updatedStudnt);
                return ResponseEntity.ok("회원 정보가 성공적으로 수정되었습니다!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("회원 정보 수정 중 오류가 발생했습니다: " + e.getMessage());
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증이 필요합니다.");
    }

    @PostMapping("/forgotPwd")
    public ResponseEntity<?> forgotPassword(@RequestParam String userId) {
        Optional<Studnt> user = studntRepository.findByStdntId(userId);

        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 아이디로 등록된 계정이 없습니다.");
        }

        try {
            String verifycode = studntService.generateVerificationCode(user.get().getStdntEmail());
            Studnt studnt = user.get();
            studnt.setVerifycode(verifycode);
            studntRepository.save(studnt);
            studntService.sendVerificationCodeEmail(studnt.getStdntEmail(), verifycode);

            // 이메일을 함께 반환
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("email", studnt.getStdntEmail());
            responseBody.put("message", "이메일 전송 성공");

            return ResponseEntity.ok(responseBody);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("인증 코드 발송에 실패했습니다.");
        }
    }


    @PostMapping("/verifyCode")
    public ResponseEntity<?> verifyCode(@RequestBody Map<String, String> requestBody) {

        String code = requestBody.get("verificationCode");

        System.out.println("입력된 인증 코드: " + code);

        // 인증 코드 확인
        Optional<Studnt> user = studntRepository.findByVerifycode(code);

        // Optional이 비어있으면 인증 코드 일치하지 않음
        if (!user.isPresent()) {
            System.out.println("찾은 사용자: 없다");
            log.error("인증 코드: {}와 일치하는 계정을 찾을 수 없습니다.", code);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 코드가 일치하지 않습니다.");
        } else {
            System.out.println("찾은 사용자: " + user.get().getStdntId());
            return ResponseEntity.ok("인증 코드 확인 완료");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> payload) {
        String newPwd = payload.get("newPwd");
        String verificationCode = payload.get("verificationCode");

        log.info("입력된 인증 코드: {}", verificationCode);

        // 인증 코드로 사용자 찾기
        Optional<Studnt> user = studntRepository.findByVerifycode(verificationCode);

        // 로그로 사용자 정보 확인
        if (!user.isPresent()) {
            log.error("인증 코드와 일치하는 사용자가 없습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 인증 코드와 일치하는 계정을 찾을 수 없습니다.");
        }

        Studnt studnt = user.get(); // 첫 번째 사용자 선택

        // 비밀번호 변경
        studnt.setPwd(passwordEncoder.encode(newPwd));

        try {
            studntRepository.save(studnt);  // 새 비밀번호 저장
            return ResponseEntity.ok("비밀번호 변경 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비밀번호 변경 중 오류가 발생했습니다.");
        }
    }




}