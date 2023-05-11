package com.example.testtoy.domain.friendrequest.service;

import com.example.testtoy.domain.friendrequest.domain.FriendRequest;
import com.example.testtoy.domain.friendrequest.repository.FriendRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;

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
    public void save(FriendRequest friendRequest){
        friendRequestRepository.save(friendRequest);
    }


}
