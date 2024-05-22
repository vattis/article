package com.example.demo.comment.domain;

import com.example.demo.article.domain.Article;
import com.example.demo.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Article article;

    @Column(nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdTime;

    public static Comment of(Article article, Member member, String content, LocalDateTime createdTime){
        return Comment.builder()
                .article(article)
                .member(member)
                .content(content)
                .createdTime(createdTime)
                .build();
    }
}
