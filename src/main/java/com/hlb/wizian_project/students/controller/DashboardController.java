package com.hlb.wizian_project.students.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
@RestController
@RequestMapping("/api")
public class DashboardController {

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard(Authentication authentication) {
        ResponseEntity<?> response = ResponseEntity.internalServerError().build();

        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            Map<String, String> studnt = Map.of(
                    "loginUser", "abc123"
            );

            response = ResponseEntity.ok().body(studnt);
        }else {
            response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패!! - 로그인하세요!!");
        }
        return response;
    }
}
