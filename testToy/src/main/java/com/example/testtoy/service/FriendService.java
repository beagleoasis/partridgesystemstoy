package com.example.testtoy.service;

import com.example.testtoy.domain.friend.FriendStatus;
import com.example.testtoy.domain.friendrequest.FriendRequest;
import com.example.testtoy.domain.member.Member;
import com.example.testtoy.repository.FriendRepository;
import com.example.testtoy.repository.FriendRequestRepository;
import com.example.testtoy.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FriendService {

    private final MemberRepository memberRepository;
    private final FriendRepository friendRepository;

    private final FriendRequestRepository friendRequestRepository;

    public FriendService(MemberRepository memberRepository, FriendRepository friendRepository, FriendRequestRepository friendRequestRepository) {
        this.memberRepository = memberRepository;
        this.friendRepository = friendRepository;
        this.friendRequestRepository = friendRequestRepository;
    }

    public void sendFriendRequest(Long senderId, Long receiverId){

        // 친구 요청을 보내는 유저(로그인한 현재 유저)
        Member sender = memberRepository.findOne(senderId);

        // 친구 요청을 받는 유저
        Member receiver = memberRepository.findOne(receiverId);

        /*     로직1     */
/*
        // sender가 receiver에게 이미 친구 요청을 한 경우,
        if(friendRequestRepository.findBySenderAndReceiver(sender, receiver).isPresent()){

            System.out.println("이미 친구 요청을 한 상태!");

            // 친구 수락 작업 수행
            Optional<FriendRequest> friendRequest = friendRequestRepository.findBySenderAndReceiver(sender,receiver);

            // 친구 처리
            friendRequest.get().setState(FriendStatus.FRIEND);
        }
        // sender가 receiver에게 친구 요청을 하지 않은 경우,
        else{
            System.out.println("친구 요청을 한 적이 없음!");
            // 친구 요청 작업 수행
            FriendRequest friendRequest = new FriendRequest();
            friendRequest.setSender(sender);
            friendRequest.setReceiver(receiver);
            friendRequest.setState(FriendStatus.REQUEST);
            friendRequestRepository.save(friendRequest);
        }
*/



        if(sender.equals(receiver)){
            // 자기 자신에게 친구 요청을 보낸 경우,
        }


        /*     로직2     */
        if (sender.getFriends1().contains(receiver) && sender.getFriends2().contains(receiver)) {
            throw new RuntimeException("해당 사용자와 이미 친구입니다.");
        }

        if (receiver.getReceivedFriendRequests().stream().anyMatch(fr -> fr.getSender().equals(sender))) {
            throw new RuntimeException("이미 해당 사용자로부터 친구 요청을 받았습니다.");
        }

        if (sender.getSentFriendRequests().stream().anyMatch(fr -> fr.getReceiver().equals(receiver))) {
            throw new RuntimeException("이미 해당 사용자에게 친구 요청을 보냈습니다.");
        }





    }

}
