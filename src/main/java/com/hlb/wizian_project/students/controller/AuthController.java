package com.hlb.wizian_project.students.controller;

import com.hlb.wizian_project.domain.Studnt;
import com.hlb.wizian_project.students.jwt.JwtTokenProvider;
import com.hlb.wizian_project.students.service.StudntService;
import com.hlb.wizian_project.students.utils.GoogleRecaptchaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/stdnt")
public class AuthController {

    private final StudntService studntService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final GoogleRecaptchaService googleRecaptchaService;

    @PostMapping("/signup")
    public ResponseEntity<?> joinok(@RequestBody Studnt studnt) {
        ResponseEntity<?> response = ResponseEntity.internalServerError().build();

        log.info("submit된 학생 정보 : {}", studnt);

        try {
            if (!googleRecaptchaService.verifyRecaptcha(studnt.getGRecaptchaResponse())) {
                throw new IllegalStateException("자동가입방지 코드 오류");
            }

            // 정상 처리시 상태코드 200으로 응답;;
            studntService.newStudnt(studnt);
            response = ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            response = ResponseEntity.badRequest().body(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @GetMapping("/check-id")
    public ResponseEntity<?> checkIdDuplicate(@RequestParam String stdntId) {
        boolean isAvailable = !studntService.existsByStdntId(stdntId);

        return ResponseEntity.ok(Map.of("isAvailable", isAvailable));
    }


    @PostMapping("/signin")
    public ResponseEntity<?> loginok(@RequestBody Studnt studnt) {
        ResponseEntity<?> response = ResponseEntity.internalServerError().build();

        log.info("submit된 로그인 정보 : {}", studnt);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(studnt.getStdntId(), studnt.getPwd())
            );

            final String jwt = jwtTokenProvider.generateToken(studnt.getStdntId());

            Map<String, String> tokens = Map.of(
                    "accessToken", jwt
            );
            response = ResponseEntity.ok().body(tokens);
        } catch (UsernameNotFoundException e) {
            response = ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("아이디가 존재하지 않습니다!!");
        } catch (BadCredentialsException e) {
            response = ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("아이디나 비밀번호가 일치하지 않습니다!!");
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("이메일 인증을 하지 않았습니다!!");
        }

        return response;
    }

}
