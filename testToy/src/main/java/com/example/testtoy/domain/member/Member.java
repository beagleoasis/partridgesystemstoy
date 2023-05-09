package com.example.testtoy.domain.member;

import com.example.testtoy.domain.friend.Friend;
import com.example.testtoy.domain.friendrequest.FriendRequest;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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

/*    // 사용자가 추가한 친구들을 나타내는 Friend 엔티티와 1:N
    @OneToMany(mappedBy = "member1")
    private Set<Friend> friends1 = new HashSet<>();

    // 사용자를 추가한 친구들을 나타내는 Friend 엔티티와 1:N
    @OneToMany(mappedBy = "member2")
    private Set<Friend> friends2 = new HashSet<>();*/

    @OneToMany(mappedBy = "member")
    private List<Friend> friends = new ArrayList<>();

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
}
