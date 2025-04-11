package com.hlb.wizian_project.admins.utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleRecaptchaService {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    private final String verifyURL = "https://www.google.com/recaptcha/api/siteverify";
    private final String secretKey = System.getenv("recaptcha.secretkey");


    public boolean verifyRecaptcha(String gRecaptchaResponse) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("secret", secretKey);
        map.add("response", gRecaptchaResponse);


        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        try {

            ResponseEntity<String> response = restTemplate.postForEntity(verifyURL, request, String.class);

            JsonNode rootNode = objectMapper.readTree(response.getBody());
            log.info("recaptcha 결과: {}", response);

            return rootNode.path("success").asBoolean();
        } catch (Exception e) {
            log.error("reCAPTCHA 검증 오류", e);
            return false;
        }
    }

}