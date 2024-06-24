package com.example.demo.friendship.controller;

import com.example.demo.friendship.domain.Friendship;
import com.example.demo.friendship.repository.FriendshipRepository;
import com.example.demo.friendship.service.FriendshipService;
import com.example.demo.login.domain.LoginConst;
import com.example.demo.member.domain.Member;
import com.example.demo.member.service.MemberService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FriendshipController {
    private final FriendshipService friendshipService;
    private final MemberService memberService;
    private final FriendshipRepository friendshipRepository;
    @GetMapping("/friends")
    public String gotoFriends(@SessionAttribute(name = LoginConst.LOGIN_MEMBER_ID, required = false) Long loginMemberId,
                              @RequestParam(name="pageNo", defaultValue = "0", required = false) int pageNo,
                              Model model){
        Member member = memberService.findOne(loginMemberId);
        model.addAttribute("friends", friendshipService.findBothAcceptedFriend(member, pageNo));
        return "/FriendList";
    }
    @GetMapping("/invitedFriends")
    public String gotoInvitedFriends(@SessionAttribute(name = LoginConst.LOGIN_MEMBER_ID, required = false) Long loginMemberId,
                                     @RequestParam(name="pageNo", defaultValue = "0", required = false) int pageNo,
                                     Model model){
        Member member = memberService.findOne(loginMemberId);
        model.addAttribute("members", friendshipService.findNonAcceptedFriend(member, pageNo));
        return "/InvitedFriends";
    }
    @GetMapping("/acceptFriendship")
    public String acceptFriendship(@SessionAttribute(name = LoginConst.LOGIN_MEMBER_ID, required = false) Long loginMemberId,
                                   @RequestParam(name="pageNo", defaultValue = "0", required = false) int pageNo,
                                   @RequestParam("memberId") Long memberId, Model model){
        Member toFriend = memberService.findOne(loginMemberId);
        Member fromFriend = memberService.findOne(memberId);
        Friendship friendship = friendshipRepository.findByFromFriendAndToFriend(fromFriend, toFriend).get();
        friendshipService.acceptFriend(toFriend, friendship);
        return "redirect:/invitedFriends";
    }
}
