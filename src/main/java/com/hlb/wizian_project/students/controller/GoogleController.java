package com.hlb.wizian_project.students.controller;

import com.hlb.wizian_project.common.jwt.JwtTokenProvider;
import com.hlb.wizian_project.domain.GoogleTokenResponse;
import com.hlb.wizian_project.domain.GoogleUserInfo;
import com.hlb.wizian_project.domain.Studnt;
import com.hlb.wizian_project.students.service.StudntService;
import lombok.RequiredArgsConstructor;
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
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/login")
    public RedirectView googleLogin() {
        String url = "https://accounts.google.com/o/oauth2/v2/auth"
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&response_type=code"
                + "&scope=profile email"
                + "&prompt=consent";
        return new RedirectView(url);
    }

    @GetMapping("/callback")
    public String googleCallback(@RequestParam("code") String code) {
        String tokenUrl = "https://oauth2.googleapis.com/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<GoogleTokenResponse> response = restTemplate.postForEntity(
                    tokenUrl, request, GoogleTokenResponse.class
            );

            String accessToken = response.getBody().getAccess_token();
            System.out.println("Google Access Token: " + accessToken);

            GoogleUserInfo userInfo = getUserInfoFromGoogle(accessToken);
            String googleId = userInfo.getSub();
            String email = userInfo.getEmail();
            String name = userInfo.getName();

            Studnt user = studntService.findOrRegisterGoogleUser(googleId, name, email);

            String token = jwtTokenProvider.generateToken(user.getStdntId());
            String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);

            return "redirect:http://localhost:3000/dashboard?nickname=" + encodedName + "&token=" + token;
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:http://localhost:3000/pageLogin";
        }
    }

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
