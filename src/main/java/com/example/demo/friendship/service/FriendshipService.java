package com.example.demo.friendship.service;

import com.example.demo.friendship.domain.Friendship;
import com.example.demo.friendship.repository.FriendshipRepository;
import com.example.demo.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    public List<Friendship> findAllByToFriend(Member toFriend){
        return friendshipRepository.findAllByToFriend(toFriend);
    }
    public List<Friendship> findAllByFromFriend(Member fromFriend){
        return friendshipRepository.findAllByFromFriend(fromFriend);
    }
    public void addFriend(Member fromFriend, Member toFriend){
        Optional<Friendship> friendship1 = friendshipRepository.findByFromFriendAndToFriend(fromFriend, toFriend);
        Optional<Friendship> friendship2;
        if(friendship1.isEmpty()){
            friendship1 = Optional.of(Friendship.of(null, fromFriend, toFriend, true, false));
            friendshipRepository.save(friendship1.get());
            friendship2 = Optional.of(Friendship.of(null, toFriend, fromFriend, false, false));
            friendshipRepository.save(friendship2.get());
        }
        else {//이미 친구추가가 돼있는 경우
            return;
        }
    }
    public void acceptFriend(Member member, Friendship friendship){
        if(Objects.equals(member.getId(), friendship.getToFriend().getId())){
            Friendship reverseFriendship = this.getReverseFriendship(friendship);
            friendship.setAccepted(true);
            reverseFriendship.setAccepted(true);
            friendship.setBothAccepted(true);
            reverseFriendship.setBothAccepted(true);
        }else{ //현재 사용자가 친구수락 대상자랑 다른 경우
            throw new RuntimeException();
        }
    }
    public void denyFriendship(Member member, Friendship friendship){
        if(Objects.equals(member.getId(), friendship.getToFriend().getId())){
            friendshipRepository.delete(getReverseFriendship(friendship));
            friendshipRepository.delete(friendship);
        }
    }
    public void deleteFriendship(Member member, Friendship friendship){
        if(Objects.equals(member.getId(), friendship.getFromFriend().getId())){
            Friendship reverseFriendship = getReverseFriendship(friendship);
            friendshipRepository.delete(friendship);
            friendshipRepository.delete(reverseFriendship);
        }
    }
    public Friendship getReverseFriendship(Friendship friendship){
        return friendshipRepository.findByFromFriendAndToFriend(friendship.getToFriend(), friendship.getFromFriend()).orElse(null);
    }
}
