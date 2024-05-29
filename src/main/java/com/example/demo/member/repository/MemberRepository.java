package com.example.demo.member.repository;


import com.example.demo.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public Member findByName(String name);
}
