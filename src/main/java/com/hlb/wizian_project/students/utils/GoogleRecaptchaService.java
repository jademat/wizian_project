package com.hlb.wizian_project.students.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleRecaptchaService {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    private final String verifyURL = "https://www.google.com/recaptcha/api/siteverify";

    @Value("${recaptcha.secretkey}")
    private String secretKey;

    public boolean verifyRecaptcha_old(String gRecaptchaResponse) {
        boolean verfiyResult = false;

        URI uri = UriComponentsBuilder.fromUriString(verifyURL)
                .queryParam("secret", secretKey)
                .queryParam("response", gRecaptchaResponse)
                .build(true).toUri();

        try {
            String jsonResponse = restTemplate.getForObject(uri, String.class);
            log.info("recaptcha 결과: {}", jsonResponse);

            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            verfiyResult = rootNode.get("success").asBoolean();
        } catch (RestClientException e) {
            log.error("API 호출 실패: {}", e.getMessage(), e);
            throw new RuntimeException("reCAPTCHA 데이터를 가져오는 중 오류가 발생했습니다", e);
        }  catch (JsonProcessingException e) {
            log.error("JSON 파싱 오류: {}", e.getMessage(), e);
            throw new RuntimeException("유효하지 않은 응답 형식입니다", e);
        }


        return verfiyResult;
    }

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

