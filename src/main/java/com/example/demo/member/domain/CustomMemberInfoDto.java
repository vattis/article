package com.example.demo.member.domain;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomMemberInfoDto {
    private Long id;
    private String memberId;
    private String name;
    private String memberPw;
    private RoleType role;
}
