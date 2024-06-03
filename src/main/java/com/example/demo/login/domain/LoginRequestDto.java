package com.example.demo.login.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    @NotNull(message = "아이디 입력은 필수 입니다")
    private String memberId;

    @NotNull(message = "패스워드 입력은 필수 입니다")
    private String password;
}
