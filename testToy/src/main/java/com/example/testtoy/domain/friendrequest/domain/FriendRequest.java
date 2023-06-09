package com.example.testtoy.domain.friendrequest.domain;

import com.example.testtoy.domain.friend.domain.FriendStatus;
import com.example.testtoy.domain.member.domain.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friendrequest_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private FriendStatus state;


    public static FriendRequest createFriendRequest(Member sender, Member receiver, FriendStatus state){
        return FriendRequest.builder()
                .sender(sender)
                .receiver(receiver)
                .state(state)
                .build();
    }

    public void updateFriendRequestStatus(FriendStatus state){
        this.state = state;
    }

}

