package com.example.demo.member.controller;

import com.example.demo.member.domain.EditMemberDto;
import com.example.demo.member.domain.Member;
import com.example.demo.member.domain.MemberDetailDto;
import com.example.demo.member.domain.MemberDto;
import com.example.demo.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

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
    @GetMapping("/editMyPage")
    public String gotoEditMyPage(Model model, HttpServletRequest request){
        Long memberId = (Long)model.getAttribute("memberId");
        MemberDto loginMember = (MemberDto) request.getAttribute("memberDto");
        log.info(memberId+"@!#!@!@#!@#!@#!@##!");
        if(loginMember != null){
            if(Objects.equals(loginMember.getId(), memberId)){
                Member member = memberService.findOne(memberId);
                EditMemberDto editMyPageDto = new EditMemberDto();
                editMyPageDto.setName(member.getName());
                editMyPageDto.setId(memberId);
                model.addAttribute("editMemberDto", editMyPageDto);
            }
            else{
                log.info("다른 유저의 페이지 접근 불가");
            }
        }
        else{
            log.info("비로그인 차단");
        }
        return "/EditMyPage";
    }
    @PostMapping("/editMyPage")
    public String editMyPage(Model model){
        EditMemberDto editMemberDto = (EditMemberDto)model.getAttribute("editMemberDto");
        Member member = memberService.findOne(editMemberDto.getId());
        memberService.updateMember(member, editMemberDto);
        return "redirect:/member?memberId=" + member.getId();
    }
}
