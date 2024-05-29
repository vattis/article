package com.example.demo.member.service;

import com.example.demo.member.domain.Member;
import com.example.demo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailService {
    private final MemberRepository memberRepository;

    public Member loadMemberByName(String name){
        return memberRepository.findByName(name);
    }
}
