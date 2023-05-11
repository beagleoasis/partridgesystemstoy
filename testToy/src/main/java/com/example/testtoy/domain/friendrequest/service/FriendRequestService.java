package com.example.testtoy.domain.friendrequest.service;

import com.example.testtoy.domain.member.domain.Member;
import com.example.testtoy.domain.friendrequest.repository.FriendRequestRepository;
import org.springframework.stereotype.Service;

@Service
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;

    public FriendRequestService(FriendRequestRepository friendRequestRepository) {
        this.friendRequestRepository = friendRequestRepository;
    }

    public void saveFriendRequest(Member sender, Member receiver){

/*
        // 이미 친구 요청을 보낸 경우, 예외 처리
        if(friendRequestRepository.existsBySenderAndReceiver(sender, receiver)){
            throw new DuplicateRequestException("친구 요청이 이미 전송되었습니다.");
        }

        // 친구 요청 생성
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(sender);
        friendRequest.setReceiver(receiver);
        friendRequest.setState(FriendStatus.PENDING);

        friendRequestRepository.save(friendRequest);
*/

    }

}
