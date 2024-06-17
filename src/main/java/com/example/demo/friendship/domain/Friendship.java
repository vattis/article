package com.example.demo.friendship.domain;

import com.example.demo.member.domain.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Setter
@Getter
@Builder
@RequiredArgsConstructor
public class Friendship {
    @Id
    Long id;

    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    private Member toFriend;

    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    private Member fromFriend;

    @Column
    Boolean accepted;

    @Column
    Boolean bothAccepted;

    public static Friendship of(Long id, Member fromFriend, Member toFriend, Boolean accepted, Boolean bothAccepted){
        return builder().id(null)
                .fromFriend(fromFriend)
                .toFriend(toFriend)
                .accepted(accepted)
                .bothAccepted(bothAccepted)
                .build();
    }
}
