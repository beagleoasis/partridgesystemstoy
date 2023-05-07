package com.example.testtoy.service;

import com.example.testtoy.domain.friend.Friend;
import com.example.testtoy.domain.friend.FriendStatus;
import com.example.testtoy.domain.friendrequest.FriendRequest;
import com.example.testtoy.domain.member.Member;
import com.example.testtoy.repository.FriendRepository;
import com.example.testtoy.repository.FriendRequestRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
public class FriendService {

    private final FriendRepository friendRepository;

    private final FriendRequestRepository friendRequestRepository;

    public FriendService(FriendRepository friendRepository, FriendRequestRepository friendRequestRepository) {
        this.friendRepository = friendRepository;
        this.friendRequestRepository = friendRequestRepository;
    }

    public void acceptFriendRequest(Member user, Member friend) throws ChangeSetPersister.NotFoundException {

        // 친구 요청이 없는 경우, 예외 처리
        FriendRequest friendRequest = friendRequestRepository.findBySenderAndReceiver(friend, user)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        // 친구 요청 승인
        friendRequest.setState(FriendStatus.FRIEND);

        // 친구 관계 생성
        Friend myFriend = new Friend();
        myFriend.setMember(user);
        myFriend.setFriend(friend);
        friendRepository.save(myFriend);

        Friend yourFriend = new Friend();
        yourFriend.setMember(friend);
        yourFriend.setFriend(user);
        friendRepository.save(yourFriend);

        friendRequestRepository.save(friendRequest);
    }

}
