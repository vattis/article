package com.example.demo.interceptor;

import com.example.demo.login.domain.LoginConst;
import com.example.demo.member.domain.Member;
import com.example.demo.member.domain.MemberDto;
import com.example.demo.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
@Slf4j
@RequiredArgsConstructor
public class LoginMemberInterceptor  implements HandlerInterceptor {
    private final MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(true);
        Long loginMemberId = (Long)session.getAttribute(LoginConst.LOGIN_MEMBER_ID);

        if(session.getAttribute(LoginConst.LOGIN_MEMBER_ID) != null){
            Member member = memberService.findOne(loginMemberId);
            request.setAttribute("memberDto", MemberDto.from(member));
        }
        request.setAttribute("asd", 12);
        return true;
    }
}
