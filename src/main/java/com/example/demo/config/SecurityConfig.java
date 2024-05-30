package com.example.demo.config;

import com.example.demo.login.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration                   //spring security 설정
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig  {   //WebSecurityConfigurerAdapter 상속받아서 하는 설정 방식은 deprecated됨.
    //대신 개발자가 직접 component-based security 설정을 할 수 있도록 변경되었다. 즉 커스텀 할 설정들을 @Bean으로 등록하여 사용한다.

    private final JwtTokenProvider jwtTokenProvider;

    private String[] allowUrl = {         //인증, 인가 작업을 할 필요없는 url들 모아둠.
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/sign/**",
            "/users/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.httpBasic().disable()
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS   //restapi 기반의 동작 방식 설정.
                )                                         // 세션이 아니니 stateless


                .and()
                .authorizeHttpRequests()       //authorizedRequests, antMatchers는 deprecated되서 사용 안 함.
                .requestMatchers(allowUrl).permitAll()
                .requestMatchers(HttpMethod.GET, "/boards/**").permitAll()  //boards로 시작하는 get요청은 다 허용한다는 의미.
//                .anyRequest().anonymous()   //기타 요청은 인증을 받지 않아도 모두 접근 가능.
                .anyRequest().authenticated()

                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())            //권한을 확인하는 과정에서 예외발생 시 전달할 예외 처리

                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())            //인증과정에서 발생하는 예외 처리

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); //후자의 필터로 가기전 Jwt필터를 먼저 거치겠다는 것.


        return httpSecurity.build();
    }
}