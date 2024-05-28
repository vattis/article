package com.example.demo.init;

import com.example.demo.article.domain.Article;
import com.example.demo.article.repository.ArticleRepository;
import com.example.demo.comment.domain.Comment;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.member.domain.Member;
import com.example.demo.member.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PostInitializer {
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    //private final EntityManager em;

    @PostConstruct
    public void init(){
        for(int i = 1; i <= 10; i++){
            Member member = Member.makeSample(i);
            Article article = Article.makeSample(member, i);
            Comment comment1 = Comment.of(article, member, "comment content A"+i, LocalDateTime.now());
            Comment comment2 = Comment.of(article, member, "comment content B"+i, LocalDateTime.now());
            member.getComments().add(comment1);
            member.getComments().add(comment2);
            article.getComments().add(comment1);
            article.getComments().add(comment2);
            memberRepository.save(member);
            articleRepository.save(article);
            commentRepository.save(comment1);
            commentRepository.save(comment2);
        }
        //em.flush();
        //em.clear();
    }
}
