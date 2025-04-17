package com.hlb.wizian_project.instructors.controller;

import com.hlb.wizian_project.common.jwt.JwtTokenProvider;
import com.hlb.wizian_project.domain.Inst;
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

@CrossOrigin(origins = {"http://localhost:3000"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/inst")
public class AuthInstController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;


    @PostMapping("/signin")
    public ResponseEntity<?> loginok(@RequestBody Inst inst) {
        ResponseEntity<?> response = ResponseEntity.internalServerError().build();

        log.info("submit된 로그인 정보 : {} {}", inst.getInstId(), inst.getPasswd());
        log.info("submit된 로그인 정보 ---- : {}", inst.getInstId());


        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(inst.getInstId(), inst.getPasswd())
            );

            final String jwt = jwtTokenProvider.generateToken(inst.getInstId());

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
