package com.example.testtoy.domain.friend;

import com.example.testtoy.domain.member.Member;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
