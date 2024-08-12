package com.example.demo.article.repository;

import com.example.demo.article.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findAll(Pageable pageable);
    Page<Article> findAllByTitleContaining(String keyword, Pageable pageable);
    Page<Article> findAllByContentContaining(String keyword, Pageable pageable);
    Page<Article> findAllByMemberNameContaining(String keyword, Pageable pageable);
}
