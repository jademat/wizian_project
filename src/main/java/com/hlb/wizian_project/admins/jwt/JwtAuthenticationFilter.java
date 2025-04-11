package com.hlb.wizian_project.admins.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain fc) throws ServletException, IOException {
        String jwt = null;
        String username = null;

        // Authorization 헤더에서 Bearer 토큰 추출
        final String authHeader = req.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7); // "Bearer " 이후의 토큰
        }

        // 쿠키에서 JWT 토큰 추출
        if (jwt == null) {
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("jwt".equals(cookie.getName())) {
                        jwt = cookie.getValue();
                        break;
                    }
                }
            }
        }

        // JWT 토큰이 있으면 username 추출
        if (jwt != null) {
            username = jwtTokenProvider.extractUsername(jwt);
        }
        log.info(">> get username : {}", username);

        log.info("JWT: {}", jwt);
        log.info("Username extracted from JWT: {}", username);

        // username이 존재하고, 인증 정보가 없다면
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.info(">> JwtAuthenticationFilter - loadUserByUsername 호출 ");
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            log.info("UserDetails: {}", userDetails);

            if (jwtTokenProvider.validateToken(jwt)) { // username 비교 없이 validateToken으로 변경
                log.info("권한 목록: {}", userDetails.getAuthorities());

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(auth);
                log.info("Authentication set for user: {}", username);
            } else {
                log.warn("토큰이 유효하지 않습니다.");
            }
        }

        // 인증 정보가 없다면 요청을 계속 진행
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            log.info(">> 인증이 없음. 요청을 계속 진행합니다.");
        }

        fc.doFilter(req, res);  // 필터 체인 계속 진행
    }
}