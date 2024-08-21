package com.example.demo.article.repository;

import com.example.demo.article.domain.Article;
import com.example.demo.comment.domain.Comment;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.member.domain.Member;
import com.example.demo.member.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class ArticleRepositoryTest {
    @Autowired ArticleRepository articleRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired CommentRepository commentRepository;
    @Autowired EntityManager em;
    @BeforeEach
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
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("save&find test")
    public void saveAndFindTest(){
        //given
        Member member = Member.makeSample(100);
        memberRepository.save(member);
        Article article = Article.makeSample(member, 100);
        articleRepository.save(article);
        PageRequest pageRequest = PageRequest.of(0, 1);
        //when
        List<Article> articles = articleRepository.findAll();
        Page<Article> foundedArticle = articleRepository.findAllByTitleContaining("title100", pageRequest);

        //then
        assertThat(articles.size()).isEqualTo(11);
        assertThat(foundedArticle.stream().toList().get(0).getTitle()).isEqualTo("title100");
    }
    @Test
    @DisplayName("delete test")
    void deleteTest(){
        //given
        List<Article> articles = articleRepository.findAll();
        Article article = articles.get(0);

        //when
        articleRepository.delete(article);
        em.flush();
        em.clear();

        //then
        List<Comment> comments = commentRepository.findAll();
        assertThat(comments.size()).isEqualTo(18);
    }
}