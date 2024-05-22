package com.example.demo.article.domain;

import com.example.demo.comment.domain.Comment;
import com.example.demo.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateTime;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
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
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return id != null && id.equals(article.id);
    }
}
