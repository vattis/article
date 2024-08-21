package com.example.demo.article.service;

import com.example.demo.article.domain.Article;
import com.example.demo.article.domain.PageConst;
import com.example.demo.article.domain.SearchType;
import com.example.demo.article.repository.ArticleRepository;
import com.example.demo.comment.domain.Comment;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.member.domain.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    public Long addArticle(Article article){
        return articleRepository.save(article).getId();
    }
    public void deleteArticle(Article article){
        articleRepository.delete(article);
    }
    public void increaseViewership(Article article){article.upViews();}
    public Optional<Article> findById(Long id){
        return articleRepository.findById(id);
    }

    public boolean likesArticle(Article article, Member member){
        if(member.getLikedArticles().contains(article.getId())){
            return false;
        }else{
            member.getLikedArticles().add(member.getId());
            article.upLikes();
            return true;
        }

    }
    public Page<Article> search(SearchType searchType, String word, int pageNo){
        Pageable pageable = PageRequest.of(pageNo, PageConst.pageSize, Sort.by("id").ascending());
        if(word == null || word.isEmpty()){
            return articleRepository.findAll(pageable);
        }
        else if(searchType == SearchType.TITLE){
            return articleRepository.findAllByTitleContaining(word, pageable);
        }
        else if(searchType == SearchType.USER){
            return articleRepository.findAllByMemberNameContaining(word, pageable);
        }
        else if(searchType == SearchType.CONTENT){
            return articleRepository.findAllByContentContaining(word, pageable);
        }
        else if(searchType == SearchType.COMMENT){
            List<Comment> comments = commentRepository.findAllByContent(word);
            int start = (int)pageable.getOffset();
            int end = Math.min((start+pageable.getPageSize()), comments.size());
        }
        else if(searchType == SearchType.ALL){
            return articleRepository.findAllByTitleContainingOrMemberNameContainingOrContentContaining(word, word, word, pageable);
        }
        return null;
    }
    public Article editArticle(Long articleId, Member member, String title, String content){
        Article article = articleRepository.findById(articleId).get();
        if(Objects.equals(article.getMember().getId(), member.getId())){
            article.setTitle(title);
            article.setContent(content);
        }
        return article;
    }
    public void deleteArticle(Long articleId, Long loginMemberId){
        Article article = articleRepository.findById(articleId).get();
        if(Objects.equals(article.getMember().getId(), loginMemberId)){
            articleRepository.delete(article);
        }
    }
}
