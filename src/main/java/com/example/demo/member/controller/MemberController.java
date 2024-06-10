package com.example.demo.member.controller;

import com.example.demo.member.domain.Member;
import com.example.demo.member.domain.MemberDetailDto;
import com.example.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member")
    public String memberInfo(@RequestParam("memberId")Long memberId, Model model){
        Member member = memberService.findOne(memberId);
        MemberDetailDto memberDetailDto = MemberDetailDto.from(member);
        model.addAttribute("memberDetailDto", memberDetailDto);
        return "/Member";
    }
}
