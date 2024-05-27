package com.example.demo.article.Controller;

import com.example.demo.article.domain.Article;
import com.example.demo.article.domain.SearchType;
import com.example.demo.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping
@Slf4j
public class ArticleController {
    private final ArticleService articleService;
    @GetMapping("/articles")
    public String gotoArticles(@RequestParam("pageNo") Integer pageNo, Model model, SearchType searchType, String search){
        if(pageNo != null){
            Page<Article> articles = articleService.search(searchType, search, pageNo);
            model.addAttribute("articles");
        }
        return "/Articles";
    }

}
