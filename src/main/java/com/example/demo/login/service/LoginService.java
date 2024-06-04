package com.example.demo.login.service;

import com.example.demo.login.domain.LoginForm;
import com.example.demo.member.domain.Member;
import com.example.demo.member.domain.MemberDto;
import com.example.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberService memberService;
    public MemberDto login(LoginForm loginForm){
        Member member = memberService.findByMemberId(loginForm.getMemberId());
        if(member == null){
            return null;
        }else{
            if(member.getMemberPw().equals(loginForm.getMemberPw())){
                return MemberDto.from(member);
            }
            else{
                return null;
            }
        }
    }
}
