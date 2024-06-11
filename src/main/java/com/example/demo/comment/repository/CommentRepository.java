package com.example.demo.comment.repository;

import com.example.demo.article.domain.Article;
import com.example.demo.comment.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByContent(String content);
    Page<Comment> findAllByArticle(Article article, Pageable pageable);
}
