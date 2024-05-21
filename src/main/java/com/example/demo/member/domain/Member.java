package com.example.demo.member.domain;

import com.example.demo.article.domain.Article;
import com.example.demo.comment.domain.Comment;
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
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;

    @Column
    private String memberId;

    @Column
    private String memberPw;

    @Column
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "member")
    private final List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private final List<Comment> comments = new ArrayList<>();

    public static Member of(String name, String memberId, String memberPw, LocalDateTime createdDate){
        return Member.builder()
                .name(name)
                .memberId(memberId)
                .memberPw(memberPw)
                .createdDate(createdDate)
                .build();
    }
    public static Member makeSample(int i){
        return new Member(null, "name"+i, "memberId"+i, "memberPw"+i, LocalDateTime.now());
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member)) return false;
        Member member = (Member) o;
        return id != null && id.equals(member.id);
    }
}
