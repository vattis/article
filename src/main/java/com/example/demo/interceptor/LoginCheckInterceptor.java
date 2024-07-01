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
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {
    private final MemberService memberService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();
        log.info("인증 체크 인터셉터 실행 {}", requestUri);
        HttpSession session = request.getSession(true);
        Long loginMemberId = (Long)session.getAttribute(LoginConst.LOGIN_MEMBER_ID);

        if(session.getAttribute(LoginConst.LOGIN_MEMBER_ID) == null){
            log.info("미인증 사용자");
            response.sendRedirect("/login?redirectURL="+requestUri);
        }else{
            Member member = memberService.findOne(loginMemberId);
            request.setAttribute("memberDto", MemberDto.from(member));
        }
        return true;
    }

}
