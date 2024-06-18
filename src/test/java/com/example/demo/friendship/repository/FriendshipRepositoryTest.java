package com.example.demo.friendship.repository;

import com.example.demo.friendship.domain.Friendship;
import com.example.demo.member.domain.Member;
import com.example.demo.member.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class FriendshipRepositoryTest {
    @Autowired FriendshipRepository friendshipRepository;
    @Autowired MemberRepository memberRepository;
    @BeforeEach
    public void init(){
        ArrayList<Member> memberList = new ArrayList<>();
        for(int i = 1; i <= 13; i++){
            Member member = Member.makeSample(i);
            memberList.add(member);
            memberRepository.save(member);
        }
        for(int i = 1; i <= 10; i++){
            Member member = memberList.get(i);
            friendshipRepository.save(Friendship.of(null, member, memberList.get(i+1), true, true));
            friendshipRepository.save(Friendship.of(null, memberList.get(i+1), member, true, true));

            friendshipRepository.save(Friendship.of(null, member, memberList.get(i+2), true, false));
            friendshipRepository.save(Friendship.of(null, memberList.get(i+2), member, false, false));
        }
    }

    @DisplayName("fromFriend Test")
    @Test
    public void fromFriendTest(){
        //given
        Member member1 = memberRepository.findByName("name5").orElse(null);
        Member member2 = memberRepository.findByName("name6").orElse(null);
        List<Friendship> fromFriendships = friendshipRepository.findAllByFromFriend(member1);

        //when
        List<Friendship> fromFriendships_false = friendshipRepository.findAllByToFriendAndAccepted(member1, false);
        List<Friendship> fromFriendships_true = friendshipRepository.findAllByToFriendAndAccepted(member1, true);
        Optional<Friendship> friendship = friendshipRepository.findByFromFriendAndToFriend(member1, member2);

        //then
        assertThat(fromFriendships.size()).isEqualTo(4);
        assertThat(fromFriendships_false.size()).isEqualTo(1);
        assertThat(fromFriendships_true.size()).isEqualTo(3);
        assertThat(friendship.get().getBothAccepted()).isEqualTo(true);
    }
}