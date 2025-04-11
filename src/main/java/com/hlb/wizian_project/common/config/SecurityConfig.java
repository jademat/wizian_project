package com.hlb.wizian_project.common.config;

import com.hlb.wizian_project.common.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable() // CSRF 필터 끔
                .authorizeRequests() // URL 기반 인가 설정
                // 관리자 로그인은 누구나 접근 가능
                .antMatchers("/api/admins/admin/login").permitAll()
                // 학생 관련 API는 누구나 접근 가능
                .antMatchers("/api/auth/**", "/api/auth/stdnt/**", "/api/auth/stdnt/forgot-password", "/api/auth/stdnt/verifyCode", "/api/auth/stdnt/reset-password").permitAll()
                // 대시보드는 인증된 사용자만 접근 가능
                .antMatchers("/api/dashboard").authenticated()
                // 관리자와 관련된 API는 인증된 사용자만 접근
                .antMatchers(HttpMethod.PUT, "/api/admins/**").authenticated()
                .antMatchers("/api/admins/**").authenticated()
                .anyRequest().permitAll()  // 나머지 모든 요청은 인증 여부와 관계없이 접근 가능
                .and()
                .sessionManagement() // JWT 인증을 위해 STATELESS 설정
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // JWT 필터 설정
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000")); // React 주소
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() { // 비밀번호 암호화에 사용할 인코더
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
