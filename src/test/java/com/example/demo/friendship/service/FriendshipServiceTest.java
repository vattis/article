package com.example.demo.friendship.service;

import com.example.demo.friendship.domain.Friendship;
import com.example.demo.friendship.repository.FriendshipRepository;
import com.example.demo.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FriendshipServiceTest {
    @Mock
    private FriendshipRepository friendshipRepository;
    @InjectMocks
    private FriendshipService friendshipService;

    @Test
    @DisplayName("addFriend Test")
    public void addFriendTest(){
        //given
        Member member1 = Member.makeSample(1);
        Member member2 = Member.makeSample(2);
        Member member3 = Member.makeSample(3);
        Member member4 = Member.makeSample(4);
        when(friendshipRepository.findByFromFriendAndToFriend(member1, member2)).thenReturn(Optional.empty());
        when(friendshipRepository.findByFromFriendAndToFriend(member3, member4)).thenReturn(Optional.of(Friendship.of(1L, member3, member4, true, true)));
        //when

        //then
    }

}