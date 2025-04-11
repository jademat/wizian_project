package com.hlb.wizian_project.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secretKey}")
    private String secretString;

    @Value("${jwt.validity}")
    private long validity;

    private Key secretKey;

    @PostConstruct
    protected void init() {
        byte[] keyBytes = secretString.getBytes();
        secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    // 토큰을 생성하는 메소드 (관리자와 학생 공통)
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + validity);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // 토큰을 검증하는 메소드 (학생용은 username도 함께 검증)
    public boolean validateToken(String token, String username) {
        final String tokenUsername = extractUsername(token);
        return tokenUsername.equals(username) && !isTokenExpired(token); // 학생 검증
    }

    // 관리자용 validateToken (username 없이 단순히 토큰만 검증)
    public boolean validateToken(String token) {
        return !isTokenExpired(token); // 관리자 검증
    }

    // username을 토큰에서 추출
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // 토큰 만료 여부 확인
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
    public String generateTokenFromRefresh(String refreshToken) {
        // 예시: Refresh token에서 정보를 추출하고, 새로운 액세스 토큰을 생성
        Claims claims = extractClaims(refreshToken);
        String username = claims.getSubject();
        return generateToken(username); // 액세스 토큰의 만료 시간을 1시간으로 설정
    }
    // 토큰에서 Claims 정보를 추출
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}