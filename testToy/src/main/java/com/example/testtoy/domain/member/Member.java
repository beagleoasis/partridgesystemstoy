package com.example.testtoy.domain.member;

import com.example.testtoy.domain.friend.Friends;
import com.example.testtoy.domain.friendrequest.FriendRequest;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    @Column(name="member_password")
    private String password;

    private String state;
    @OneToMany(mappedBy = "member")
    private List<Friends> friends = new ArrayList<>();

    // 사용자가 보낸 친구 요청을 나타내는 FriendRequest 엔티티와 1:N
    @OneToMany(mappedBy = "sender")
    private List<FriendRequest> sentFriendRequests = new ArrayList<>();

    // 사용자가 받은 친구 요청을 나타내는 FriendRequest 엔티티와 1:N
    @OneToMany(mappedBy = "receiver")
    private List<FriendRequest> receivedFriendRequests = new ArrayList<>();

    public static Member createMember(String name, String password){
        return Member.builder()
                .name(name)
                .password(password)
                .build();
    }

    public void updateMemberState(String state){
        this.state = state;
    }

}
