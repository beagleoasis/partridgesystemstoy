package com.example.testtoy.domain.member;

import com.example.testtoy.domain.friend.Friend;
import com.example.testtoy.domain.friendrequest.FriendRequest;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    @Column(name="member_password")
    private String password;

    private String state;

    // 사용자가 추가한 친구들을 나타내는 Friend 엔티티와 1:N
    @OneToMany(mappedBy = "member1")
    private Set<Friend> friends1 = new HashSet<>();

    // 사용자를 추가한 친구들을 나타내는 Friend 엔티티와 1:N
    @OneToMany(mappedBy = "member2")
    private Set<Friend> friends2 = new HashSet<>();

    // 사용자가 보낸 친구 요청을 나타내는 FriendRequest 엔티티와 1:N
    @OneToMany(mappedBy = "sender")
    private Set<FriendRequest> sentFriendRequests = new HashSet<>();

    // 사용자가 받은 친구 요청을 나타내는 FriendRequest 엔티티와 1:N
    @OneToMany(mappedBy = "receiver")
    private Set<FriendRequest> receivedFriendRequests = new HashSet<>();


}
