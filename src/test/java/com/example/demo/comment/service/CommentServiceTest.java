package com.example.demo.comment.service;

import com.example.demo.article.domain.Article;
import com.example.demo.article.service.ArticleService;
import com.example.demo.comment.domain.Comment;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.member.domain.Member;
import com.example.demo.member.service.MemberService;
import jakarta.persistence.Column;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @InjectMocks private CommentService commentService;
    @Mock private ArticleService articleService;
    @Mock private MemberService memberService;
    @Mock private CommentRepository commentRepository;

    @Test
    @DisplayName("add comment Test")
    public void addCommentTest(){
        //given
        Member member = Member.makeSample(1);
        Article article = Article.makeSample(member,1);
        given(articleService.findById(1L)).willReturn(article);
        given(memberService.findOne(1L)).willReturn(member);
        //when
        commentService.addComment(1L, 1L, "content1");
        //then
        assertThat(member.getComments().get(0).getContent()).isEqualTo("content1");
        assertThat(article.getComments().get(0).getContent()).isEqualTo("content1");
    }
}