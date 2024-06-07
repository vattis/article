package com.example.demo.login.controller;

import com.example.demo.login.domain.LoginConst;
import com.example.demo.login.domain.LoginForm;
import com.example.demo.login.service.LoginService;
import com.example.demo.member.domain.Member;
import com.example.demo.member.domain.MemberDto;
import com.example.demo.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.net.http.HttpRequest;
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;
    private final LoginService loginService;
    @GetMapping("/login")
    public String gotoLoginPage(@SessionAttribute(value = "loginMemberId", required = false) String memberId, Model model){
        if(!checkLogin(memberId)){
            model.addAttribute("loginForm", new LoginForm());
            return "/Login";
        }
        else{
            return "redirect:/articles";
        }
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult,
                        @SessionAttribute(value="loginMemberId", required = false) String memberId,
                        HttpServletRequest request, HttpServletResponse response, Model model){
        if(checkLogin(memberId)){ //로그인 된 상태로 다시 로그인 시도
            return "redirect:/articles";
        }
        else{
            if(Objects.equals(loginForm.getMemberId(), "")){
                bindingResult.addError(new FieldError("loginForm", "memberId", "아이디를 입력해주세요"));
                log.info("로그인 실패: 아이디 입력 누락");
                return "redirect:/login";
            }
            if(Objects.equals(loginForm.getMemberPw(), "")){
                bindingResult.addError(new FieldError("loginForm", "memberPw", "비밀번호를 입력해주세요"));
                log.info("로그인 실패: 비밀번호 입력 누락");
                return "redirect:/login";
            }
            MemberDto memberDto = loginService.login(loginForm);
            if(memberDto == null){
                bindingResult.addError(new ObjectError("loginForm", "해당하는 회원이 없습니다"));
                log.info("잘못된 로그인 정보");
                return "redirect:/login";
            }else{
                model.addAttribute("memberDto", memberDto);
                HttpSession session = request.getSession();
                session.setAttribute(LoginConst.LOGIN_MEMBER_ID, memberDto.getId());
                Cookie cookie = new Cookie(LoginConst.LOGIN_MEMBER_ID, String.valueOf(memberDto.getId()));
                response.addCookie(cookie);
            }
        }
        return "redirect:/articles";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        session.invalidate();
        Cookie cookie = new Cookie(LoginConst.LOGIN_MEMBER_ID, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/articles";
    }


    public boolean checkLogin(String memberId){
        return memberId != null;
    }
}
