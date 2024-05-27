package com.example.demo.comment.service;

import com.example.demo.article.domain.Article;
import com.example.demo.article.service.ArticleService;
import com.example.demo.comment.domain.Comment;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.member.domain.Member;
import com.example.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final ArticleService articleService;
    private final MemberService memberService;
    private final CommentRepository commentRepository;

    public void addComment(Long articleId, Long memberId, String commentContent){
        Article article = articleService.findById(articleId);
        Member member = memberService.findOne(memberId);
        Comment comment = Comment.of(article, member, commentContent, LocalDateTime.now());
        article.getComments().add(comment);
        member.getComments().add(comment);
        commentRepository.save(comment);
    }

    public void deleteComment(Long articleId, Long memberId, Comment comment){
        Article article = articleService.findById(articleId);
        Member member = memberService.findOne(memberId);
        if(memberId.equals(comment.getMember().getId())){
            article.getComments().remove(comment);
            member.getComments().remove(comment);
            commentRepository.delete(comment);
        }
    }
}
