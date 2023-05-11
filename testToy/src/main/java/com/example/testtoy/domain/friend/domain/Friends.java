package com.example.testtoy.domain.friend.domain;

import com.example.testtoy.domain.member.domain.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "friend")
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "friend_id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "friend_member_id")
    private Member friendMember;

    public static Friends createFriend(Member member, Member friendMember){
        return Friends.builder()
                .member(member)
                .friendMember(friendMember)
                .build();
    }

}
