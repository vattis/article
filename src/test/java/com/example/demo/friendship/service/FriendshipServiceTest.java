package com.example.demo.friendship.service;

import com.example.demo.friendship.domain.Friendship;
import com.example.demo.friendship.repository.FriendshipRepository;
import com.example.demo.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FriendshipServiceTest {
    @Mock
    private FriendshipRepository friendshipRepository;
    @Spy
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
        friendshipService.addFriend(member1, member2);
        friendshipService.addFriend(member3, member4);
        //then
        verify(friendshipRepository, times(2)).save(any());
        //verify(friendshipRepository, times(1)).save(Friendship.of(null, member2, member1, false, false));
    }
    @Test
    @DisplayName("addFriend Test")
    public void addFriendTest2(){
        //given
        Member member3 = Member.makeSample(3);
        Member member4 = Member.makeSample(4);
        when(friendshipRepository.findByFromFriendAndToFriend(member3, member4)).thenReturn(Optional.of(Friendship.of(1L, member3, member4, true, true)));
        //when
        friendshipService.addFriend(member3, member4);
        //then
        verify(friendshipRepository, never()).save(any());
    }

    @Test
    @DisplayName("acceptFriend")
    public void acceptFriendTest(){
        //given
        Member member1 = new Member(1L, "name1", "memberId1", "memberPw1", LocalDateTime.now(), new HashSet<>());
        Member member2 = new Member(2L, "name2", "memberId2", "memberPw2", LocalDateTime.now(), new HashSet<>());
        Friendship friendship1 = Friendship.of(1L, member1, member2, true, false);
        Friendship friendship2 = Friendship.of(1L, member2, member1, false, false);
        Friendship spyFriendship1 = spy(friendship1);
        Friendship spyFriendship2 = spy(friendship2);
        when(friendshipRepository.findByFromFriendAndToFriend(spyFriendship1.getToFriend(), spyFriendship1.getFromFriend())).thenReturn(Optional.of(spyFriendship2));
        //when
        friendshipService.acceptFriend(member2, spyFriendship1);

        //then
        verify(spyFriendship1, times(1)).setAccepted(true);
        verify(spyFriendship1, times(1)).setBothAccepted(true);
        verify(friendshipService, times(1)).getReverseFriendship(spyFriendship1);
    }

}