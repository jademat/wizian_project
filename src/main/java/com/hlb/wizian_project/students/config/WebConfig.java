package com.hlb.wizian_project.students.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // UTF-8 인코딩 설정
        restTemplate.getMessageConverters().add(
                0, new StringHttpMessageConverter(StandardCharsets.UTF_8)
        );

        return restTemplate;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 모든 엔드포인트에 대해
                .allowedOrigins("http://localhost:3000", "http://localhost:5173")
                .allowedMethods("*")  // GET, POST, PUT, DELETE 등 모두 허용
                .allowedHeaders("*")
                .allowCredentials(true);
    }

}

