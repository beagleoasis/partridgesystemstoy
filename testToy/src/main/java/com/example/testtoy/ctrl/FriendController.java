package com.example.testtoy.ctrl;

import com.example.testtoy.domain.friend.Friends;
import com.example.testtoy.domain.member.Member;
import com.example.testtoy.dto.SendFriendRequestDto;
import com.example.testtoy.service.FriendRequestService;
import com.example.testtoy.service.FriendService;
import com.example.testtoy.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    public ResponseEntity<String> sendFriendRequest(@RequestBody SendFriendRequestDto sendFriendRequestDto){

        String result = friendService.sendFriendRequest(sendFriendRequestDto.getSenderId(), sendFriendRequestDto.getReceiverId());

        return ResponseEntity.ok(result);
    }


    @GetMapping("")
    public String getFriends(@PageableDefault(page = 0, size = 10) Pageable pageable, Model model, HttpSession session){

        // 여기부터 해당 로그인 유저의 친구를 찾아오는 로직 구현 및 타임리프, 페이징 수정
        Member member = (Member)session.getAttribute("user");

        Page<Friends> friends = friendService.findAllFriends(pageable, member.getId());

        model.addAttribute("friends", friends);

        return "friends/friends";
    }

}
