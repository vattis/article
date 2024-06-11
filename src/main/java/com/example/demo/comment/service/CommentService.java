package com.example.demo.comment.service;

import com.example.demo.article.domain.Article;
import com.example.demo.article.domain.PageConst;
import com.example.demo.article.service.ArticleService;
import com.example.demo.comment.domain.Comment;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.member.domain.Member;
import com.example.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public void addComment(Article article, Member member, String commentContent){
        Comment comment = Comment.of(article, member, commentContent, LocalDateTime.now());
        article.getComments().add(comment);
        member.getComments().add(comment);
        commentRepository.save(comment);
    }

    public void deleteComment(Article article, Long memberId, Comment comment){
        Member member = memberService.findOne(memberId);
        if(memberId.equals(comment.getMember().getId())){
            article.getComments().remove(comment);
            member.getComments().remove(comment);
            commentRepository.delete(comment);
        }
    }
    public Page<Comment> getCommentWithPage(Article article, int pageNo){
        Pageable pageable = PageRequest.of(pageNo, PageConst.pageSize, Sort.by("createdTime").ascending());
        return commentRepository.findAllByArticle(article, pageable);
    }

}
