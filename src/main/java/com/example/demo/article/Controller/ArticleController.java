package com.example.demo.article.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ArticleController {
    @GetMapping("/articles")
    public String gotoArticles(){
        return "/Articles";
    }
}
