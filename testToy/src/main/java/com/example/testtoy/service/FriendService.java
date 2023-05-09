package com.example.testtoy.service;

import com.example.testtoy.domain.friend.FriendStatus;
import com.example.testtoy.domain.friendrequest.FriendRequest;
import com.example.testtoy.domain.member.Member;
import com.example.testtoy.repository.FriendRepository;
import com.example.testtoy.repository.FriendRequestRepository;
import com.example.testtoy.repository.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Iterator;
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

    public ResponseEntity sendFriendRequest(Long senderId, Long receiverId){

        // 친구 요청을 보내는 유저(로그인한 현재 유저)
        Member sender = memberRepository.findOne(senderId);

        // 친구 요청을 받는 유저
        Member receiver = memberRepository.findOne(receiverId);

        FriendRequest fromSenderToReceiver = friendRequestRepository.existsFriendRequestsBySender_IdAndAndReceiver_Id(senderId,receiverId);
        FriendRequest fromReceiverToSender = friendRequestRepository.existsFriendRequestsBySender_IdAndAndReceiver_Id(receiverId,senderId);
        System.out.println("fromSenderToReceiver : " + fromSenderToReceiver + " , fromReceiverToSender : " + fromReceiverToSender);

        if(fromSenderToReceiver!=null && fromReceiverToSender!=null){
            System.out.println("친구 관계!");
        } else if (fromSenderToReceiver!=null && fromReceiverToSender==null) {
            System.out.println("이미 친구 요청을 보냈습니다.");
        } else if (fromSenderToReceiver==null && fromReceiverToSender!=null) {
            System.out.println("이미 친구 요청을 받은 상태입니다.");
        } else{
            System.out.println("서로 간 친구 요청 내역 없음.");
        }


        return ResponseEntity.ok(200);

    }


}
