package com.example.demo.core.init;

import com.example.demo.article.domain.Article;
import com.example.demo.article.repository.ArticleRepository;
import com.example.demo.comment.domain.Comment;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.friendship.domain.Friendship;
import com.example.demo.friendship.repository.FriendshipRepository;
import com.example.demo.member.domain.Member;
import com.example.demo.member.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class PostInitializer {
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final FriendshipRepository friendshipRepository;
    //private final EntityManager em;

    @PostConstruct
    public void init(){
        ArrayList<Member> members = new ArrayList<>();
        for(int i = 1; i <= 100; i++){
            Member member = Member.makeSample(i);
            members.add(member);
            Article article = Article.makeSample(member, i);
            Comment comment1 = Comment.of(article, member, "comment content A"+i, LocalDateTime.now());
            Comment comment2 = Comment.of(article, member, "comment content B"+i, LocalDateTime.now());
            member.getComments().add(comment1);
            member.getComments().add(comment2);
            article.getComments().add(comment1);
            article.getComments().add(comment2);
            memberRepository.save(member);
            articleRepository.save(article);
            commentRepository.save(comment1);
            commentRepository.save(comment2);
        }
        for(int i = 0; i < 90; i++){
            Member member1 = members.get(i);
            Member member2 = members.get(i+1);
            Member member3 = members.get(i+2);
            Friendship friendship1 = Friendship.of(null, member1, member2, true, true);
            Friendship reverseFriendship1 = Friendship.of(null, member2, member1, true, true);
            Friendship friendship2 = Friendship.of(null, member1, member3, true, false);
            Friendship reverseFriendship2 = Friendship.of(null, member3, member1, false, false);
            friendshipRepository.save(friendship1);
            friendshipRepository.save(friendship2);
        }
        //em.flush();
        //em.clear();
    }
}
