package com.example.demo.login.jwt;

import com.example.demo.member.domain.CustomMemberInfoDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {
    private final Key key;
    private final long accessTokenExpTime;

    public JwtUtil(@Value("${spring.jwt.secret}") String secretKey,
                   @Value("${spring.jwt.expiration_time}") Long accessTokenExpTime){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpTime = accessTokenExpTime;
    }

    public String createAccessToken(CustomMemberInfoDto member){
        return createToken(member, accessTokenExpTime);
    }

    private String createToken(CustomMemberInfoDto member, long expireTime){
        Claims claims = Jwts.claims();
        claims.put("id", member.getId());
        claims.put("memberId", member.getMemberId());
        claims.put("role", member.getRole());

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(expireTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(key, SignatureAlgorithm.ES256)
                .compact();
    }
    public Long getId(String token){
        return parseClaims(token).get("id", Long.class);
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
            log.info("Invalid JWT Token", e);
        }catch(ExpiredJwtException e){
            log.info("Expire JWT Token", e);
        }catch(UnsupportedJwtException e){
            log.info("Unsupported JWT token", e);
        }catch(IllegalArgumentException e){
            log.info("JWT claims string is empty", e);
        }
        return false;
    }

    public Claims parseClaims(String accessToken){
        try{
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        }catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }
}
