package com.example.demo.member.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditMemberDto {
    Long id;
    String name;
    String password;
    String passwordConfirm;
}
