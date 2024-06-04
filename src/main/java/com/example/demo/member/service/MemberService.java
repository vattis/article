package com.example.demo.member.service;

import com.example.demo.member.domain.Member;
import com.example.demo.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public Member findOne(Long id){
        return memberRepository.findById(id).orElse(null);
    }
    public Long addMember(Member member){
        return memberRepository.save(member).getId();
    }

    public Member findByMemberId(String memberId){return memberRepository.findMemberByMemberId(memberId).orElse(null); }
}
