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
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/{memberId}")
    public String memberInfo(@PathVariable("memberId")Long memberId, Model model){
        Member member = memberService.findOne(memberId);
        MemberDetailDto memberDetailDto = MemberDetailDto.from(member);
        model.addAttribute("memberDetailDto", memberDetailDto);
        return "/Member";
    }
    @GetMapping("/editMyPage")
    public String gotoEditMyPage(Model model, HttpServletRequest request, @RequestParam("memberId")Long memberId){
        MemberDto loginMember = (MemberDto) request.getAttribute("memberDto");
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
    @PutMapping("/members/{memberId}")
    public String editMyPage(@PathVariable("memberId") Long memberId, Model model, EditMemberDto editMemberDto){
        Member member = memberService.findOne(editMemberDto.getId());
        memberService.updateMember(member, editMemberDto);
        return "redirect:/members/" + member.getId();
    }
}
