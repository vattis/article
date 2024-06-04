package com.example.demo.member.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberDto {
    private Long id;
    private String memberId;
    private String name;

    public static MemberDto from(Member member){
        return MemberDto.builder()
                .id(member.getId())
                .memberId(member.getMemberId())
                .name(member.getName())
                .build();
    }
}
