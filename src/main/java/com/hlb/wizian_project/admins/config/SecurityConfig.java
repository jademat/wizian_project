package com.hlb.wizian_project.admins.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and() // ✅ CORS 허용!
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/**").permitAll()
                .anyRequest().permitAll();

        return http.build();
    }

    // ✅ CORS 실제 origin 허용
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:3000", "http://127.0.0.1:3000")
                        .allowedMethods("*");
            }
        };
    }
}
