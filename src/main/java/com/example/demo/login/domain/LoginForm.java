package com.example.demo.login.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {
    @NotNull
    private String memberId;
    @NotNull
    private String memberPw;
}
