package com.example.demo.comment.domain;

import com.example.demo.article.domain.Article;
import com.example.demo.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

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

    @ManyToOne(optional = false)
    private Article article;

    @ManyToOne(optional = false)
    private Member member;

    @Column
    private String content;

    @Column
    private LocalDateTime createdTime;

}
