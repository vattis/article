package com.example.demo.login.domain;

import com.example.demo.member.domain.Member;
import com.example.demo.member.domain.MemberDto;
import com.example.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

@RequiredArgsConstructor
public class LoginTool {
    private final MemberService memberService;
    public void loginMemberSet(Long loginMemberId, Model model){
        if(loginMemberId != null){
            Member member = memberService.findOne(loginMemberId);
            model.addAttribute("memberDto", MemberDto.from(member));
        }
    }
}
