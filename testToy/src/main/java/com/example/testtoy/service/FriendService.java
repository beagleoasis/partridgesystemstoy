package com.example.testtoy.service;

import com.example.testtoy.domain.friend.Friends;
import com.example.testtoy.domain.friend.FriendStatus;
import com.example.testtoy.domain.friendrequest.FriendRequest;
import com.example.testtoy.domain.member.Member;
import com.example.testtoy.repository.FriendRepository;
import com.example.testtoy.repository.FriendRequestRepository;
import com.example.testtoy.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    /**
    *
    * @method : sendFriendRequest
    *
    * @explain : 친구 요청 및 수락
    * @author : User
    * @date : 2023-05-10
    *
    **/
    public String sendFriendRequest(Long senderId, Long receiverId){

        // 응답값
        String result = "";

        // 친구 요청을 보내는 유저(로그인한 현재 유저)
        Member sender = memberRepository.findOne(senderId);

        // 친구 요청을 받는 유저
        Member receiver = memberRepository.findOne(receiverId);

        // 친구 요청을 했거나, 받은 적이 있는지 확인하기 위한 객체 2개 생성
        FriendRequest fromSenderToReceiver = friendRequestRepository.existsFriendRequestsBySender_IdAndAndReceiver_Id(senderId,receiverId);
        FriendRequest fromReceiverToSender = friendRequestRepository.existsFriendRequestsBySender_IdAndAndReceiver_Id(receiverId,senderId);

        if(fromSenderToReceiver!=null && fromReceiverToSender!=null){

            result = "이미 친구 관계입니다.";

        } else if (fromSenderToReceiver!=null && fromReceiverToSender==null) {

            result = "이미 친구 요청을 보냈습니다.";

        } else if (fromSenderToReceiver==null && fromReceiverToSender!=null) {

            // 친구 수락 후 친구 관계 처리
            FriendRequest friendRequest = FriendRequest.createFriendRequest(sender,receiver, FriendStatus.FRIEND);
            friendRequestRepository.save(friendRequest);

            // 기존 유저 친구 관계 FRIEND로 변경
            fromReceiverToSender.updateFriendRequestStatus(FriendStatus.FRIEND);
            friendRequestRepository.save(fromReceiverToSender);

            // Friend 테이블에 친구 생성
            Friends friend = Friends.createFriend(sender, receiver);
            friendRepository.save(friend);

            result = "친구 요청을 수락했습니다.";

        } else{
            // 친구 요청 생성
            FriendRequest friendRequest = FriendRequest.createFriendRequest(sender,receiver, FriendStatus.REQUEST);
            friendRequestRepository.save(friendRequest);
            result = "친구 요청을 완료했습니다.";
        }

        return result;
    }

    public Page<Friends> findAllFriends(Pageable pageable, Long memberId){

        return friendRepository.findFriendsByMember_Id(pageable, memberId);
    }


}
