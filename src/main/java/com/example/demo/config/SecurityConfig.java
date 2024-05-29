package com.example.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final JwtTokenProvider
}
