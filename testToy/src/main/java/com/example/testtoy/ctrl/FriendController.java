package com.example.testtoy.ctrl;

import com.example.testtoy.dto.SendFriendRequestDto;
import com.example.testtoy.service.FriendRequestService;
import com.example.testtoy.service.FriendService;
import com.example.testtoy.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("friends")
@Controller
public class FriendController {

    private final MemberService memberService;

    private final FriendService friendService;

    private final FriendRequestService friendRequestService;

    public FriendController(MemberService memberService, FriendService friendService, FriendRequestService friendRequestService) {
        this.memberService = memberService;
        this.friendService = friendService;
        this.friendRequestService = friendRequestService;
    }

    /**
    *
    * @method : sendFriendRequest
    *
    * @explain : 친구 요청 및 수락
    * @author : User
    * @date : 2023-05-09
    *
    **/
    @PostMapping("request")
    @ResponseBody
    public ResponseEntity sendFriendRequest(@RequestBody SendFriendRequestDto sendFriendRequestDto){

        return ResponseEntity.ok(friendService.sendFriendRequest(sendFriendRequestDto.getSenderId(), sendFriendRequestDto.getReceiverId()));
    }

}
