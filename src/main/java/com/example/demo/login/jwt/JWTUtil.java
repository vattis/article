package com.example.demo.login.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    private SecretKey secretKey;

    // @Value : application.yml에서의 특정한 변수 데이터를 가져올 수 있음
    // string key는 jwt에서 사용 안하므로 객체 키 생성!
    // "${spring.jwt.secret}" : application.yml에 저장된 spring: jwt: secret 에 저장된 암호화 키 사용
    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {

        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
    }

    // loginId 반환 메서드
    public String getLoginId(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJwt(token).getBody().get("loginId", String.class);
    }

    // role 반환 메서드
    public String getRole(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("role", String.class);
    }

    // 토큰이 소멸 (유효기간 만료) 하였는지 검증 메서드
    public Boolean isExpired(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    // 토큰 생성 메서드
    public String createJwt(String loginId, String role, Long expiredMs) {

        return Jwts.builder()
                .claim("loginId", loginId)
                .claim("role", role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }
}
