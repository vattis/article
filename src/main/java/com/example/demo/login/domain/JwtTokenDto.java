package com.example.demo.login.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtTokenDto {
    String accessToken;
    String refreshToken;
}
