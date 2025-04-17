package com.hlb.wizian_project.students.controller;

import com.hlb.wizian_project.common.jwt.JwtTokenProvider;
import com.hlb.wizian_project.domain.GoogleTokenResponse;
import com.hlb.wizian_project.domain.GoogleUserInfo;
import com.hlb.wizian_project.domain.Studnt;
import com.hlb.wizian_project.students.service.StudntService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/api/oauth/google")
@RequiredArgsConstructor
public class GoogleController {

    @Value("${google.client.id}")
    private String clientId;

    @Value("${google.redirect.uri}")
    private String redirectUri;

    @Value("${google.client.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate;
    private final StudntService studntService;
    private static String AccessToken = null;
    private final JwtTokenProvider jwtTokenProvider;

//    @GetMapping("/login")
//    public RedirectView googleLogin() {
//        String url = "https://accounts.google.com/o/oauth2/v2/auth"
//                + "?client_id=" + clientId
//                + "&redirect_uri=" + redirectUri
//                + "&response_type=code"
//                + "&scope=profile email"
//                + "&prompt=consent";
//        return new RedirectView(url);
//    }

    @PostMapping("/googleToken")
    public ResponseEntity<?> googleToken(@RequestBody Map<String, String> googleToken) {
        ResponseEntity<?> response = ResponseEntity.internalServerError().build();

        AccessToken = googleToken.get("access_token");
        log.info("액세스 토큰: {}", AccessToken);

        try {
            // 구글 사용자 정보 가져오기
            GoogleUserInfo googleUserInfo = getUserInfo(AccessToken);
            String googleId = googleUserInfo.getSub();
            String name = googleUserInfo.getName();
            String email = googleUserInfo.getEmail();
            System.out.println("구글 사용자 정보: " + googleId + ", " + name + ", " + email);

            // DB에서 사용자 등록 or 조회 (서비스 통해)
            Studnt user = studntService.findOrRegisterGoogleUser(googleId, name, email);

            // JWT 토큰 발급
            String jwtToken = jwtTokenProvider.generateToken(user.getStdntId());

            // 생성한 토큰을 JSON 형식으로 반환
            Map<String, String> tokens = Map.of(
                    "accessToken", jwtToken
            );
            response = ResponseEntity.ok().body(tokens);

        } catch (Exception e) {
            e.printStackTrace();
            response = ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("아이디나 비밀번호가 일치하지 않습니다!!");
        }

        return response;
    }

    // 구글 사용자 정보 요청 (내부 메서드)
    private GoogleUserInfo getUserInfo(String accessToken) {
        String userInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo";

        // Authorization 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<GoogleUserInfo> response = restTemplate.exchange(
                userInfoUrl,
                HttpMethod.GET,
                request,
                GoogleUserInfo.class
        );

        return response.getBody();
    }

//    @GetMapping("/callback")
//    public ResponseEntity<?> googleCallback(@RequestParam("code") String code) {
//        ResponseEntity<?> response = ResponseEntity.internalServerError().build();
//        String tokenUrl = "https://oauth2.googleapis.com/token";
//
//        AccessToken = googleToken.get("access_token");
//
//        log.info("액세스 토큰: {}", AccessToken);
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("code", code);
//        params.add("client_id", clientId);
//        params.add("client_secret", clientSecret);
//        params.add("redirect_uri", redirectUri);
//        params.add("grant_type", "authorization_code");
//
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
//
//        try {
//            ResponseEntity<GoogleTokenResponse> response = restTemplate.postForEntity(
//                    tokenUrl, request, GoogleTokenResponse.class
//            );
//
//            String accessToken = response.getBody().getAccess_token();
//            System.out.println("Google Access Token: " + accessToken);
//
//            // 구글 사용자 정보 가져오기
//            GoogleUserInfo userInfo = getUserInfoFromGoogle(accessToken);
//            String googleId = userInfo.getSub();
//            String email = userInfo.getEmail();
//            String name = userInfo.getName();
//
//            // DB에 구글 사용자 등록
//            Studnt user = studntService.findOrRegisterGoogleUser(googleId, name, email);
//
//            // JWT 토큰 발급
//            String token = jwtTokenProvider.generateToken(user.getStdntId());
//            String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
//
//            // 대시보드로 리디렉션
//            return "redirect:http://localhost:3000/dashboard?nickname=" + encodedName + "&token=" + token;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "redirect:http://localhost:3000/pageLogin";
//        }
//    }


    private GoogleUserInfo getUserInfoFromGoogle(String accessToken) {
        String userInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<GoogleUserInfo> response = restTemplate.exchange(
                userInfoUrl,
                HttpMethod.GET,
                request,
                GoogleUserInfo.class
        );

        return response.getBody();
    }
}
