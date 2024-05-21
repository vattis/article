package com.example.demo.member.service;

import com.example.demo.member.domain.Member;
import com.example.demo.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @InjectMocks private MemberService memberService;
    @Mock private MemberRepository memberRepository;

    @Test
    @DisplayName("save & find Test")
    public void saveTest(){
        //given
        Member member = new Member(1L, "memberName1", "memberId1", "memberPw1", LocalDateTime.now());
        given(memberRepository.save(member)).willReturn(member);
        given(memberRepository.findById(1L)).willReturn(Optional.of(member));
        //when
        memberService.addMember(member);
        Member findMember = memberService.findOne(1L);
        //then
        assertThat(member).isEqualTo(findMember);
    }
}