package com.hlb.wizian_project.admins.controller;

import com.hlb.wizian_project.common.jwt.JwtTokenProvider;
import com.hlb.wizian_project.admins.service.AdminService;
import com.hlb.wizian_project.common.utils.GoogleRecaptchaService;
import com.hlb.wizian_project.domain.Admin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admins/admin")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminService adminService;
    private final GoogleRecaptchaService googleRecaptchaService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/list")
    public Page<Admin> getAdmins(
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10, sort = "adminNo", direction = Sort.Direction.DESC) Pageable pageable) {

        return adminService.getAdmins(search, pageable);
    }

    @GetMapping("/detail/{adminNo}")
    public ResponseEntity<?> getAdmin(@PathVariable int adminNo) {
        Optional<?> admin = adminService.getAdminById(adminNo);
        return admin.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

   @PostMapping("/create")
    public ResponseEntity<?> createAdmin(@RequestBody Admin admin) {
       ResponseEntity<?> response = ResponseEntity.internalServerError().build();
       try {
           if (!googleRecaptchaService.verifyRecaptcha(admin.getGRecaptchaResponse())) {
               throw new IllegalStateException("자동가입방지 코드 오류!!");
           }
           adminService.newAdmin(admin);
           response = ResponseEntity.ok().build();
       } catch (IllegalStateException e) {
           response = ResponseEntity.badRequest().body(e.getMessage());
           e.printStackTrace();
       } catch (Exception e) {
           e.printStackTrace();
       }
       return response;
   }

   @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Admin admin) {
        ResponseEntity<?> response = ResponseEntity.internalServerError().build();
       try {
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(admin.getLoginId(),admin.getPswd()));

           // 인증이 완료되면 jwt 토큰 생성
           final String jwt = jwtTokenProvider.generateToken(admin.getLoginId());

           // 생성한 토큰을 JSON 형식으로 만듦
           Map<String,String> tokens = Map.of(
                   "accessToken",jwt
           );
           response = ResponseEntity.ok().body(tokens);
       } catch (UsernameNotFoundException e) {
           response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디가 존재하지 않습니다.");
       } catch (BadCredentialsException e) {
           response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디나 비밀번호 불일치");
       }

       return response;
   }


    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        if (refreshToken != null && refreshToken.startsWith("Bearer ")) {
            refreshToken = refreshToken.substring(7); // 'Bearer ' 부분 제거
        }

        if (refreshToken != null && jwtTokenProvider.validateToken(refreshToken)) {
            String newAccessToken = jwtTokenProvider.generateTokenFromRefresh(refreshToken);
            // JSON 응답을 반환해야 합니다.
            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));  // 수정된 부분
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid refresh token");
    }

}