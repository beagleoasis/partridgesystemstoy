package com.example.testtoy.domain.friendrequest.service;

import com.example.testtoy.domain.friendrequest.domain.FriendRequest;
import com.example.testtoy.domain.friendrequest.repository.FriendRequestRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;

    public FriendRequestService(FriendRequestRepository friendRequestRepository) {
        this.friendRequestRepository = friendRequestRepository;
    }

    /**
    *
    * @method : getFriendRequest
    *
    * @explain : 친구 요청 내역 조회
    * @author : User
    * @date : 2023-05-11
    *
    **/
    public Optional<FriendRequest> getFriendRequest(Long senderId, Long receiverId){
        return friendRequestRepository.existsFriendRequestsBySender_IdAndAndReceiver_Id(senderId, receiverId);
    }

    /**
    *
    * @method : save
    *
    * @explain : 친구 요청 생성
    * @author : User
    * @date : 2023-05-11
    *
    **/
    public FriendRequest save(FriendRequest friendRequest){
        return friendRequestRepository.save(friendRequest);
    }


}
