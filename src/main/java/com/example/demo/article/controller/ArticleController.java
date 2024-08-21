package com.example.demo.article.controller;

import com.example.demo.article.domain.Article;
import com.example.demo.article.domain.EditArticleDto;
import com.example.demo.article.domain.SearchType;
import com.example.demo.article.service.ArticleService;
import com.example.demo.comment.domain.Comment;
import com.example.demo.comment.service.CommentService;
import com.example.demo.login.domain.LoginConst;
import com.example.demo.member.domain.Member;
import com.example.demo.member.domain.MemberDto;
import com.example.demo.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping()
@Slf4j
public class ArticleController {
    private final ArticleService articleService;
    private final CommentService commentService;
    private final MemberService memberService;
    @GetMapping("/articles")
    public String gotoArticles(@RequestParam(value="searchTag", defaultValue = "") String searchTag,
                               @RequestParam(value="searchWord", defaultValue = "") String searchWord,
                               @RequestParam(value="pageNo", defaultValue = "1", required = false) Integer pageNo,
                               @SessionAttribute(name = LoginConst.LOGIN_MEMBER_ID, required = false) Long loginMemberId,
                               Model model){
        log.info("searchTag:{}, searchWord:{}", searchTag, searchWord);
        SearchType searchType = SearchType.ALL;
        if(searchTag != null){
            searchType = switch (searchTag) {
                case "title" -> SearchType.TITLE;
                case "content" -> SearchType.CONTENT;
                case "user" -> SearchType.USER;
                case "comment" -> SearchType.COMMENT;
                default -> SearchType.ALL;
            };
        }
        Page<Article> articles = articleService.search(searchType, searchWord, pageNo-1);
        model.addAttribute("articles", articles);
        model.addAttribute("pageNo", pageNo);
        return "/Articles";
    }

    @GetMapping("/articles/{articleId}")
    public String gotoArticle(@PathVariable("articleId") Long articleId, @RequestParam(defaultValue = "0") int pageNo, Model model,
                              @SessionAttribute(name = LoginConst.LOGIN_MEMBER_ID, required = false) Long loginMemberId){
        Article article = articleService.findById(articleId);
        articleService.increaseViewership(article);
        Page<Comment> commentPage = commentService.getCommentWithPage(article, pageNo);
        model.addAttribute("article", article);
        model.addAttribute("comments", commentPage);
        int commentPageNum = article.getComments().size()/20 + 1;
        model.addAttribute("commentPageNum", commentPageNum);
        model.addAttribute("maxPageSize", 10);
        return "/Article";
    }
    @GetMapping("/addArticle")
    public String goto_add_article(){
        return "/AddArticle";
    }

    @PatchMapping("/articles/{articleId}/like")
    public String likes(@SessionAttribute(name = LoginConst.LOGIN_MEMBER_ID, required = false) Long loginMemberId,
                        @PathVariable("articleId")Long articleId, Model model) throws AccessDeniedException {
        if(loginMemberId == null){
            throw new AccessDeniedException("회원만 추천할 수 있습니다.");
        }
        Member viewer = memberService.findOne(loginMemberId);
        Article article = articleService.findById(articleId);
        articleService.likesArticle(article, viewer);
        return "redirect:/articles/"+articleId;
    }
    public void loginMemberSet(Long loginMemberId, Model model){
        if(loginMemberId != null){
            Member member = memberService.findOne(loginMemberId);
            model.addAttribute("memberDto", MemberDto.from(member));
        }
    }
    @GetMapping("/editArticle")
    public String gotoEditArticle(@RequestParam("articleId") Long articleId, Model model,
                                  @SessionAttribute(name = LoginConst.LOGIN_MEMBER_ID, required = false) Long loginMemberId){
        Article article = articleService.findById(articleId);
        if(Objects.equals(article.getMember().getId(), loginMemberId)){
            EditArticleDto editArticleDto = EditArticleDto.of(loginMemberId, articleId, article.getTitle(), article.getContent(), LocalDateTime.now());
            model.addAttribute("editArticleDto", editArticleDto);
            loginMemberSet(loginMemberId, model);
        }
        return "/EditArticle";
    }
    @PutMapping("/articles/{articleId}")
    public String editArticle(@ModelAttribute("editArticleDto")EditArticleDto editArticleDto,
                              @SessionAttribute(name = LoginConst.LOGIN_MEMBER_ID, required = false) Long loginMemberId){
        if(Objects.equals(editArticleDto.getMemberId(), loginMemberId)){
            Member member = memberService.findOne(editArticleDto.getMemberId());
            editArticleDto.setEditedDate(LocalDateTime.now());
            articleService.editArticle(editArticleDto.getArticleId(), member, editArticleDto.getTitle(), editArticleDto.getContent());
        }
        return "redirect:/articles/"+editArticleDto.getArticleId();
    }
    @DeleteMapping("/articles/{articleId}")
    public String deleteArticle(@PathVariable("articleId")Long articleId,
                                @SessionAttribute(name = LoginConst.LOGIN_MEMBER_ID, required = false) Long loginMemberId){
        articleService.deleteArticle(articleId, loginMemberId);
        return "redirect:/articles";
    }
}
