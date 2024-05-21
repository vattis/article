package com.example.demo.member.repository;

import com.example.demo.member.domain.Member;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @BeforeEach
    void init(){
        for(int i = 1; i <= 10; i++){
            memberRepository.save(Member.makeSample(i));
        }
    }

    @Test
    @DisplayName("save and find test")
    public void saveAndFindTest(){
        Member member = Member.makeSample(30);
        //given
        Long id = memberRepository.save(member).getId();
        //when
        em.flush();
        em.clear();
        Member addedMember = memberRepository.findById(id).get();
        List<Member> members = memberRepository.findAll();

        //then
        assertThat(member).isEqualTo(addedMember);
        assertThat(members.size()).isEqualTo(11);
    }
}