package com.example.demo.login.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor    //생성자로 의존성 주입받을 때 final 필드들은 이 어노테이션으로 주입 가능
public class JwtTokenProvider {
    private final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final UserDetailsService userDetailsService;
    //private final RedisTemplate redisTemplate;

    @Value("${springboot.jwt.secret}")
    private String secretKey = "secretKey";

    private final long tokenValidMilliSecond = 1000L * 60 * 60;
    private final long refreshValidMilliSecond = tokenValidMilliSecond * 24;

    @PostConstruct        //해당 객체가 주입된 이후 수행되는 메서드 지정
    protected void init() {
        log.info("init ==> secret 키 초기화 시작");
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));  //secret key를 base64형식으로 인코딩한다.
        log.info("init ==> secret 키 초기화 완료");
    }
    public String createToken(String username, List<String> roles){
        log.info("JwtToken createToken ==> 토큰 생성 시작");
        Claims claims = Jwts.claims().setSubject(username);  //sub속성 값을 추가.
        claims.put("roles", roles);

        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)           //iat 속성에 값 추가. jwt가 발급된 시간.
                .setExpiration(new Date(now.getTime() + tokenValidMilliSecond))  //jwt의 만료시간
                .signWith(SignatureAlgorithm.HS256, secretKey)    //secret값 세팅. 암호화 알고리즘 적용.
                .compact();  //최종적으로 사용자에게 전달할 형태로 jwt를 컴팩트.

        log.info("JwtToken createToken ==> 토큰 생성 완료");
        return token;
    }
    public Authentication getAuthentication(String token){   //인증이 성공하면 SecurityContextHolder에 저장할 authentication을 생성하는 역할.
        log.info("JwtToken getAuthentication ==> 토큰 인증 시작" );
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    public String resolveToken(HttpServletRequest request){
        log.info("JwtToken resolveToken ==> http 헤더로부터 token 값 추출 시작");
        return request.getHeader("X-AUTH-TOKEN");    //헤더값으로 전달된 X-AUTH-TOKEN값을 가져와 리턴한다. 헤더 이름은 임의로 변경가능
    }

    public boolean validateToken(String token){
        log.info("JwtToken validateToken ==> 토큰 유효성 체크 시작");
        try{

            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build()  //jws = json web signature(서명)
                    .parseClaimsJws(token);  //header, payload 파싱해서 json 형태로 변환 -> 지정된 비밀키로 서명 검증.

            return !claims.getBody().getExpiration().before(new Date());
        } catch(Exception e){
            log.info("JwtToken validateToken ==> 예외 발생");
            return false;
        }
    }
}