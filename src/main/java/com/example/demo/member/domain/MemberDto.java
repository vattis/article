package com.example.demo.member.domain;

import com.example.demo.friendship.domain.Friendship;
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
    public static MemberDto fromMemberToToMember(Friendship friendship){
        Member member = friendship.getToFriend();
        return MemberDto.builder()
                .id(member.getId())
                .memberId(member.getMemberId())
                .name(member.getName())
                .build();
    }
    public static MemberDto toMemberToFromMember(Friendship friendship){
        Member member = friendship.getFromFriend();
        return MemberDto.builder()
                .id(member.getId())
                .memberId(member.getMemberId())
                .name(member.getName())
                .build();
    }
}
