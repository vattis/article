package com.example.demo.friendship.repository;

import com.example.demo.friendship.domain.Friendship;
import com.example.demo.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    public List<Friendship> findAllByFromFriend(Member fromFriend);

    public List<Friendship> findAllByToFriend(Member toFriend);

    public List<Friendship> findAllByToFriendAndAccepted(Member toFriend, Boolean accepted);

    public Page<Friendship> findByFromFriendAndBothAccepted(Member fromFriend, Boolean bothAccepted, Pageable pageable);
    public Page<Friendship> findByToFriendAndAccepted(Member member, Boolean accepted, Pageable pageable);
    public Optional<Friendship> findByFromFriendAndToFriend(Member fromFriend, Member toFriend);
}
