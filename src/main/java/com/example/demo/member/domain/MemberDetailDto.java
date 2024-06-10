package com.example.demo.member.domain;

import com.example.demo.article.domain.Article;
import com.example.demo.comment.domain.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class MemberDetailDto {
    private Long id;
    private String memberId;
    private String name;
    private LocalDateTime createdDate;
    private List<Article> articles;
    private List<Comment> comments;

    public static MemberDetailDto from(Member member){
        return MemberDetailDto.builder()
                .id(member.getId())
                .memberId(member.getMemberId())
                .name(member.getName())
                .createdDate(member.getCreatedDate())
                .articles(member.getArticles())
                .comments(member.getComments())
                .build();
    }
}

