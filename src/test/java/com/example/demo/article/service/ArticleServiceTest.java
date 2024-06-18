package com.example.demo.article.service;

import com.example.demo.article.domain.Article;
import com.example.demo.article.repository.ArticleRepository;
import com.example.demo.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    @InjectMocks ArticleService articleService;
    @Mock ArticleRepository articleRepository;

    @Test
    @DisplayName("search test")
    public void searchTest(){
        //given
        Member member = new Member(1L, "memberName1", "memberId1", "memberPw1", LocalDateTime.now(), new HashSet<>());
        Article article1 = Article.of(member, "title1", "content1", 0L, 0L);
        Article article2 = Article.of(member, "title2", "content2", 0L, 0L);
        List<Article> articles = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(0, 2);
        articles.add(article1);
        articles.add(article2);
        given(articleRepository.findAll()).willReturn(articles);
        //given(articleRepository.findAllByMemberName("memberName1", pageRequest)).willReturn()
        //when


        //then
    }

    @Test
    @DisplayName("save & find test")
    public void saveAndFindTest(){

    }

}