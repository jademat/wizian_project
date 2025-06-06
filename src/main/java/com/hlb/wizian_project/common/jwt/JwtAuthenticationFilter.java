package com.hlb.wizian_project.common.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
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
import java.io.IOException;@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    // @Qualifier를 사용하여 어떤 UserDetailsService를 사용할지 명시적으로 지정
    @Qualifier("customAdminDetailsService")  // 관리자용 UserDetailsService 빈을 주입
    private final UserDetailsService userDetailsServiceForAdmin;

    @Qualifier("customUserDetailsService")  // 학생용 UserDetailsService 빈을 주입
    private final UserDetailsService userDetailsServiceForStudent;

    @Qualifier("customInstDetailsService")  // 강사용 UserDetailsService 빈을 주입
    private final UserDetailsService userDetailsServiceForInst;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain fc) throws ServletException, IOException {
        String jwt = null;
        String username = null;

        final String authHeader = req.getHeader("Authorization");
        log.info(">> Authorization 헤더: {}", authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7); // "Bearer " 이후의 토큰
        }
        log.info(">> Extracted JWT from Header: {}", jwt);

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
        log.info(">> 최종 JWT: {}", jwt);

        if (jwt == null) {
            log.warn(">> JWT가 요청에서 제공되지 않음");
            fc.doFilter(req, res);  // 필터 체인 계속 진행
            return;
        }

        try {
            username = jwtTokenProvider.extractUsername(jwt);
            log.info(">> JWT에서 추출한 username: {}", username); // username 로그 찍기
        } catch (Exception e) {
            log.error(">> JWT 파싱 실패: {}", e.getMessage()); // 예외 로그 출력
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "잘못된 JWT 형식입니다.");
            return;
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.info(">> JwtAuthenticationFilter - loadUserByUsername 호출 ");
            UserDetails userDetails = null;

            // 관리자와 학생을 구분하여 적절한 UserDetailsService를 사용
            if (isAdmin(req)) {
                userDetails = userDetailsServiceForAdmin.loadUserByUsername(username);
            } else if (isInstructor(req)) {
                userDetails = userDetailsServiceForInst.loadUserByUsername(username);
            } else {
                userDetails = userDetailsServiceForStudent.loadUserByUsername(username);
            }


            if (userDetails != null && jwtTokenProvider.validateToken(jwt)) {
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(auth);
                log.info(">> 인증 성공: {}", username);
            } else {
                log.warn("유효하지 않은 토큰입니다.");
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다.");
                return;
            }
        }

        fc.doFilter(req, res);  // 필터 체인 계속 진행
    }

    // 요청이 관리자인지 학생인지를 판단하는 메소드
    private boolean isAdmin(HttpServletRequest req) {
        return req.getRequestURI().startsWith("/admin"); // /admin으로 시작하는 요청을 관리자로 판단
    }

    // 요청이 강사인지 판단하는 메소드
    private boolean isInstructor(HttpServletRequest req) {
        return req.getRequestURI().startsWith("/teacher"); // /teacher으로 시작하는 요청을 관리자로 판단
    }
}
