package com.example.demo.article.controller;

import com.example.demo.article.domain.Article;
import com.example.demo.article.domain.SearchType;
import com.example.demo.article.service.ArticleService;
import com.example.demo.login.domain.LoginConst;
import com.example.demo.member.domain.Member;
import com.example.demo.member.domain.MemberDto;
import com.example.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
@RequestMapping()
@Slf4j
public class ArticleController {
    private final ArticleService articleService;
    private final MemberService memberService;
    @GetMapping("/articles")
    public String gotoArticles(@RequestParam(value="pageNo", defaultValue = "0", required = false) Integer pageNo ,
                               @SessionAttribute(name = LoginConst.LOGIN_MEMBER_ID, required = false) Long loginMemberId,
                               Model model){
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
        if(loginMemberId != null){
            Member member = memberService.findOne(loginMemberId);
            model.addAttribute("memberDto", MemberDto.from(member));
        }
        return "/Articles";
    }

    @GetMapping("/article")
    public String gotoArticle(@RequestParam Long articleId, Model model){
        Article article = articleService.findById(articleId);
        model.addAttribute("article", article);
        return "/Article";
    }
    @GetMapping("/addArticle")
    public String goto_add_article(){
        return "/AddArticle";
    }

}
