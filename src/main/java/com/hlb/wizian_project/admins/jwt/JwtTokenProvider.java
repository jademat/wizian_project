package com.hlb.wizian_project.admins.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
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
    private Long validity;

    private Key secretKey;

    @PostConstruct
    protected void init() {
        byte[] keyBytes = secretString.getBytes();
        secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    private String generateToken(String username, long expirationTime) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationTime); // expirationTime 인자를 사용

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String generateToken(String username) {
        return generateToken(username, validity); // 기본 validtiy 사용
    }

    public boolean validateToken(String token) {
        return !isTokenExpired(token); // username 비교가 필요 없으므로, 단순히 만료 여부만 확인
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    public String generateTokenFromRefresh(String refreshToken) {
        // 예시: Refresh token에서 정보를 추출하고, 새로운 액세스 토큰을 생성
        Claims claims = extractClaims(refreshToken);
        String username = claims.getSubject();
        return generateToken(username, 3600000); // 액세스 토큰의 만료 시간을 1시간으로 설정
    }

    public String generateAccessToken(String username) {
        return generateToken(username, 3600000); // 1시간 만료 시간
    }
}