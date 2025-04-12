package com.hlb.wizian_project.students.controller;

import com.hlb.wizian_project.common.jwt.JwtTokenProvider;
import com.hlb.wizian_project.domain.KakaoTokenResponse;
import com.hlb.wizian_project.domain.KakaoUserInfo;
import com.hlb.wizian_project.domain.Studnt;
import com.hlb.wizian_project.students.repository.StudntRepository;
import com.hlb.wizian_project.students.service.StudntService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/oauth/kakao")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class KakaoController {

    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.redirect.uri}")
    private String redirectUri;

    @Value("${kakao.redirect.logout.uri}")
    private String redirectLogoutUri;

    private final RestTemplate restTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private static String AccessToken = null;
    private final StudntRepository studntRepository;
    private final StudntService studntService;

    // 카카오 로그인 - 로그인 후 인가 코드 받기
    @GetMapping("/login")
    public String kakaoLogin() {
        String authorizeUrl = "https://kauth.kakao.com/oauth/authorize";
        String params = "?client_id=%s&redirect_uri=%s&response_type=code&prompt=login";
        String kakaoUrl = String.format(authorizeUrl+params, clientId, redirectUri);

        return "redirect:"+kakaoUrl;
    }

    // 카카오 인증 후 redirect 엔드포인트 - 인가 코드를 이용해서 액세스토큰 받기
    @GetMapping("/callback")
    public String kakaoCallback(@RequestParam String code) {
        log.info("인가 코드: {}", code);

        String authorizeUrl = "https://kauth.kakao.com/oauth/token";
        String params = "?client_id=%s&redirect_uri=%s&code=%s&grant_type=authorization_code";
        String kakaoUrl = String.format(authorizeUrl + params, clientId, redirectUri, code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            // 1. 토큰 요청
            ResponseEntity<KakaoTokenResponse> response = restTemplate.postForEntity(
                    kakaoUrl, request, KakaoTokenResponse.class
            );

            KakaoTokenResponse tokenResponse = response.getBody();
            if (tokenResponse != null) {
                AccessToken = tokenResponse.getAccess_token();
                log.info("Access token: {}", AccessToken);
            }

            // 2. 사용자 정보 요청
            KakaoUserInfo kakaoUserInfo = getUserInfo(AccessToken);
            String kakaoId = kakaoUserInfo.getId().toString();
            String nickname = kakaoUserInfo.getProperties().getNickname();
            String email = (kakaoUserInfo.getKakao_account() != null && kakaoUserInfo.getKakao_account().getEmail() != null)
                    ? kakaoUserInfo.getKakao_account().getEmail()
                    : kakaoId + "@kakao.com"; // 기본 이메일 형식

            // 3. DB 사용자 등록 or 조회 (서비스 통해)
            Studnt user = studntService.findOrRegisterKakaoUser(kakaoId, nickname, email); // ✅ 변경된 부분

            // 4. JWT 발급
            String encodedNickname = URLEncoder.encode(user.getStdntNm(), StandardCharsets.UTF_8);
            String token = jwtTokenProvider.generateToken(user.getStdntId());

            return "redirect:http://localhost:3000/dashboard?nickname=" + encodedNickname + "&token=" + token;

        } catch (Exception e) {
            log.error("Error getting token or user info: {}", e.getMessage());
            return "redirect:http://localhost:3000/pageLogin";
        }
    }



    @GetMapping("/myinfo")
    @ResponseBody
    public ResponseEntity<KakaoUserInfo> getMyInfo(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String accessToken = authHeader.substring(7); // "Bearer " 제외
        try {
            KakaoUserInfo userInfo = getUserInfo(accessToken);
            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            log.error("사용자 정보 조회 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 4. 토큰으로 사용자 정보 요청 (내부 메서드)
    private KakaoUserInfo getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<KakaoUserInfo> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                request,
                KakaoUserInfo.class
        );

        return response.getBody();
    }

    // 카카오 로그아웃
    @GetMapping("/logout")
    // public ResponseEntity<String> kakaoLogout() {
    public String kakaoLogout() {
        // 카카오가 발급한 액세스 토큰 무효화 - 재로그인 시 아이디/비번 다시 입력 없음
        String logoutUrl = "https://kapi.kakao.com/v1/user/logout";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.set("Authorization", "Bearer " + AccessToken);

        System.out.println("Using token: " + AccessToken);
        System.out.println("Authorization header: " + headers.get("Authorization"));

        // HTTP 요청 엔티티 생성
        HttpEntity<String> request = new HttpEntity<>(headers);

        // GET 요청으로 사용자 정보 받기
        ResponseEntity<String> response = restTemplate.exchange(
                logoutUrl, HttpMethod.POST,
                request, String.class
        );

        log.info("Logout Response: {}", response.getStatusCode());

        // return ResponseEntity.ok("로그아웃 성공!!");

        // 완전한 로그아웃 - 재로그인 시 아이디/비번 다시 입력 필요!
        logoutUrl = "https://kauth.kakao.com/oauth/logout";
        String params = String.format("?client_id=%s&logout_redirect_uri=%s", clientId, redirectLogoutUri);

        return "redirect:" + logoutUrl + params;
    }
}

