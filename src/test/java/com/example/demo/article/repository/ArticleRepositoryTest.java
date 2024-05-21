package com.example.demo.article.repository;

import com.example.demo.article.domain.Article;
import com.example.demo.member.domain.Member;
import com.example.demo.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class ArticleRepositoryTest {
    @Autowired ArticleRepository articleRepository;
    @Autowired
    MemberRepository memberRepository;
    @BeforeEach
    public void init(){
        for(int i = 1; i <= 10; i++){
            Member member = Member.makeSample(i);
            memberRepository.save(member);
            Article article = Article.makeSample(member, i);
            articleRepository.save(article);
        }
    }

    @Test
    @DisplayName("save&find test")
    public void saveAndFindTest(){

    }
}