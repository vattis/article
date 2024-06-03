package com.example.demo.login.service;

import com.example.demo.login.domain.LoginRequestDto;

public interface AuthService {
    String login(LoginRequestDto dto);
}
