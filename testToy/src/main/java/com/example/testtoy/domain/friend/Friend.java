package com.example.testtoy.domain.friend;

import com.example.testtoy.domain.member.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "friend")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "friend_member_id")
    private Member friendMember;

    public static Friend createFriend(Member member, Member friendMember){
        return Friend.builder()
                .member(member)
                .friendMember(friendMember)
                .build();
    }

}
