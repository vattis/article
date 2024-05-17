package com.example.demo.article.service;

import com.example.demo.article.domain.Article;
import com.example.demo.article.domain.PageConst;
import com.example.demo.article.domain.SearchType;
import com.example.demo.article.repository.ArticleRepository;
import com.example.demo.comment.domain.Comment;
import com.example.demo.comment.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    @Transactional
    Page<Article> search(SearchType searchType, String word, int pageNo){
        Pageable pageable = PageRequest.of(pageNo, PageConst.pageSize, Sort.by("id").ascending());
        if(word.isEmpty()){
            return articleRepository.findAll(pageable);
        }
        else if(searchType == SearchType.TITLE){
            return articleRepository.findAllByTitle(word, pageable);
        }
        else if(searchType == SearchType.USER){
            return articleRepository.findAllByMemberName(word, pageable);
        }
        else if(searchType == SearchType.COMMENT){
            List<Comment> comments = commentRepository.findAllByContent(word);
            int start = (int)pageable.getOffset();
            int end = Math.min((start+pageable.getPageSize()), comments.size());
        }
        return null;
    }
}
