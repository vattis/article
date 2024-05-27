package com.example.demo.comment.repository;

import com.example.demo.article.domain.Article;
import com.example.demo.article.repository.ArticleRepository;
import com.example.demo.comment.domain.Comment;
import com.example.demo.member.domain.Member;
import com.example.demo.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class CommentRepositoryTest {
    @Autowired private MemberRepository memberRepository;
    @Autowired private ArticleRepository articleRepository;
    @Autowired private CommentRepository commentRepository;

    @BeforeEach
    void init(){
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
    }
    @Test
    @DisplayName("find test")
    void findTest(){
        //given

        //when
        List<Article> articles = articleRepository.findAll();
        List<Comment> comments = articles.get(0).getComments();
        //then
        assertThat(comments.size()).isEqualTo(2);

    }

    @Test
    @DisplayName("delete test")
    void deleteTest(){
        //given

        //when

        //then

    }
}