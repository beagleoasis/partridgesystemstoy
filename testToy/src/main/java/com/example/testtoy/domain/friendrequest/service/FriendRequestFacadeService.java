package com.example.testtoy.domain.friendrequest.service;

import com.example.testtoy.domain.friend.domain.FriendStatus;
import com.example.testtoy.domain.friend.domain.Friends;
import com.example.testtoy.domain.friend.service.FriendService;
import com.example.testtoy.domain.friendrequest.domain.FriendRequest;
import com.example.testtoy.domain.member.domain.Member;
import com.example.testtoy.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendRequestFacadeService {

    private final FriendService friendService;

    private final FriendRequestService friendRequestService;

    private final MemberService memberService;

    /**
     *
     * @method : sendFriendRequest
     *
     * @explain : 친구 요청 및 수락
     * @author : User
     * @date : 2023-05-10
     *
     **/
    @Transactional
    public String sendFriendRequest(Long senderId, Long receiverId){

        // 응답값
        String result = "";

        // 친구 요청을 보내는 유저(로그인한 현재 유저)
        Member sender = memberService.findOneById(senderId);

        // 친구 요청을 받는 유저
        Member receiver = memberService.findOneById(receiverId);

        // 친구 요청을 했거나, 받은 적이 있는지 확인하기 위한 객체 2개 생성
        Optional<FriendRequest> fromSenderToReceiver = friendRequestService.getFriendRequest(senderId,receiverId);

        Optional<FriendRequest> fromReceiverToSender = friendRequestService.getFriendRequest(receiverId,senderId);

        if(fromSenderToReceiver.isPresent() && fromReceiverToSender.isPresent()){

            result = "이미 친구 관계입니다.";

        } else if (fromSenderToReceiver.isPresent() && !fromReceiverToSender.isPresent()) {

            result = "이미 친구 요청을 보냈습니다.";

        } else if (!fromSenderToReceiver.isPresent() && fromReceiverToSender.isPresent()) {

            // 친구 수락 후 친구 관계 처리
            FriendRequest friendRequest = FriendRequest.createFriendRequest(sender,receiver, FriendStatus.FRIEND);
            friendRequestService.save(friendRequest);

            // 기존 유저 친구 관계 FRIEND로 변경
            fromReceiverToSender.orElseThrow().updateFriendRequestStatus(FriendStatus.FRIEND);

            // Friend 테이블에 친구 생성
            Friends friends = Friends.createFriend(sender,receiver);
            friendService.save(friends);

            result = "친구 요청을 수락했습니다.";

        } else {

            // 친구 요청 생성
            FriendRequest friendRequest = FriendRequest.createFriendRequest(sender,receiver,FriendStatus.REQUEST);
            friendRequestService.save(friendRequest);

            result = "친구 요청을 완료했습니다.";

        }

        return result;
    }

}
