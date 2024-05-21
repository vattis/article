package com.example.demo.article.domain;

import com.example.demo.comment.domain.Comment;
import com.example.demo.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(optional = false)
    private Member member;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private LocalDateTime dateTime;

    @OneToMany(mappedBy = "article")
    private List<Comment> comments = new ArrayList<>();

    public static Article makeSample(Member member, int i){
        return Article.builder()
                .member(member)
                .title("title"+i)
                .content("content"+i)
                .dateTime(LocalDateTime.now())
                .build();
    }
    public static Article of(Member member, String title, String content){
        return Article.builder()
                .member(member)
                .title(title)
                .content(content)
                .dateTime(LocalDateTime.now())
                .build();
    }
}
