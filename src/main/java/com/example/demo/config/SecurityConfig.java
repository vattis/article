package com.example.demo.config;


import com.example.demo.filter.JwtAuthenticationFilter;
import com.example.demo.handler.CustomAccessDeniedHandler;
import com.example.demo.handler.CustomAuthenticationEntryPoint;
import com.example.demo.login.jwt.JwtProvider;
import com.example.demo.login.jwt.JwtUtil;
import com.example.demo.member.service.CustomMemberDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final CustomMemberDetailService customMemberDetailService;
    private final JwtUtil jwtUtil;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    private static final String[] AUTH_WHITELIST = {
            "/api/v1/member/**",
            "/swagger-ui/**",
            "/api-docs",
            "/swagger-ui-custom.html",
            "/v3/api-docs/**",
            "/api-docs/**",
            "/swagger-ui.html",
            "/api/v1/auth/**"
    };
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf((csrf)->csrf.disable());
        http.cors(Customizer.withDefaults());

        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS));
        http.formLogin((form)->form.disable());
        http.httpBasic(AbstractHttpConfigurer::disable);

        http.addFilterBefore(new JwtAuthenticationFilter(customMemberDetailService, jwtUtil), UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling((exceptionHandling)->exceptionHandling
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
        );
        http.authorizeHttpRequests(authorize->authorize
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().permitAll()
                .anyRequest().authenticated()
        );
        return http.build();
    }

}