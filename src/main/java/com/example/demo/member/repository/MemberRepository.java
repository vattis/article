package com.example.demo.member.repository;


import com.example.demo.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public Optional<Member> findByName(String name);
    public Optional<Member> findMemberByMemberId(String memberId);
}
