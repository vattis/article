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
        Optional<Friendship> friendship2 = getReverseFriendship(friendship1.get());
        if(friendship1.isEmpty()){
            friendship1 = Optional.of(Friendship.of(null, fromFriend, toFriend, true, false));
            friendshipRepository.save(friendship1.get());

        }
        else {//이미 친구추가가 돼있는 경우

        }
        if(friendship2.isEmpty()){
            friendship2 = Optional.of(Friendship.of(null, toFriend, fromFriend, false, false));
            friendshipRepository.save(friendship2.get());
        }
        else{//이미 친구추가가 돼있는 경우

        }
    }
    public void acceptFriend(Member member, Friendship friendship){
        if(Objects.equals(member.getId(), friendship.getToFriend().getId())){
            Optional<Friendship> reverseFriendship = getReverseFriendship(friendship);
            friendship.setAccepted(true);
            reverseFriendship.get().setAccepted(true);
            friendship.setBothAccepted(true);
            reverseFriendship.get().setBothAccepted(true);
        }else{
            throw new RuntimeException();
        }
    }
    public void denyFriendship(Member member, Friendship friendship){
        if(Objects.equals(member.getId(), friendship.getToFriend().getId())){
            friendshipRepository.delete(friendship);
        }
    }
    public Optional<Friendship> getReverseFriendship(Friendship friendship){
        return friendshipRepository.findByFromFriendAndToFriend(friendship.getToFriend(), friendship.getFromFriend());
    }
}
