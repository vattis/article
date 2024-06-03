package com.example.demo.login.controller;

import com.example.demo.login.domain.LoginRequestDto;
import com.example.demo.login.jwt.JwtUtil;
import com.example.demo.login.service.AuthService;
import com.example.demo.member.repository.MemberRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthApiController{
    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<String> getMemberProfile(@Valid @RequestBody LoginRequestDto request){
        String token = this.authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}