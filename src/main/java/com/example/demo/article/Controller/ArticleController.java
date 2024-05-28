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

import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping()
@Slf4j
public class ArticleController {
    private final ArticleService articleService;
    @GetMapping("/articles")
    public String gotoArticles(@RequestParam(value="pageNo", defaultValue = "0", required = false) Integer pageNo , Model model){
        String searchTag = (String)model.getAttribute("searchTag");
        String searchWord = (String)model.getAttribute("word");
        SearchType searchType = SearchType.ALL;
        if(searchTag != null){
            searchType = switch (searchTag) {
                case "title" -> SearchType.TITLE;
                case "content" -> SearchType.CONTENT;
                case "user" -> SearchType.USER;
                case "comment" -> SearchType.COMMENT;
                default -> searchType;
            };
        }
        Page<Article> articles = articleService.search(searchType, searchWord, pageNo);
        model.addAttribute("articles", articles);
        return "/Articles";
    }

}
