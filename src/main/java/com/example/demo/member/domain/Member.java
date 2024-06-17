package com.example.demo.member.domain;

import com.example.demo.article.domain.Article;
import com.example.demo.comment.domain.Comment;
import com.example.demo.friendship.domain.Friendship;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Builder
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class Member{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String memberId;

    @Column(nullable = false)
    private String memberPw;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "member")
    private final List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private final List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "toFriend")
    private final List<Friendship> toFriends = new ArrayList<>();

    @OneToMany(mappedBy = "fromFriend")
    private final List<Friendship> fromFriends = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name="LIKED_ARTICLES")
    public Set<Long> likedArticles = new HashSet<>();

    public static Member of(String name, String memberId, String memberPw, LocalDateTime createdDate){
        return Member.builder()
                .name(name)
                .memberId(memberId)
                .memberPw(memberPw)
                .createdDate(createdDate)
                .build();
    }
    public static Member makeSample(int i){
        return new Member(null, "name"+i, "memberId"+i, "memberPw"+i, LocalDateTime.now(), new HashSet<>());
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member)) return false;
        Member member = (Member) o;
        return id != null && id.equals(member.id);
    }
}
