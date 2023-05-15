package com.example.testtoy.domain.friend.controller;

import com.example.testtoy.domain.friend.domain.Friends;
import com.example.testtoy.domain.friendrequest.service.FriendRequestFacadeService;
import com.example.testtoy.domain.member.domain.Member;
import com.example.testtoy.domain.friendrequest.domain.SendFriendRequestDto;
import com.example.testtoy.domain.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequestMapping("friends")
@Controller
@RequiredArgsConstructor
public class FriendController {

    private final FriendRequestFacadeService friendRequestFacadeService;

    private final FriendService friendService;

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

        String result = friendRequestFacadeService.sendFriendRequest(sendFriendRequestDto.getSenderId(), sendFriendRequestDto.getReceiverId());

        return ResponseEntity.ok(result);
    }


    @GetMapping("")
    public String getFriends(@PageableDefault(page = 0, size = 10) Pageable pageable, Model model, HttpSession session){

        // 여기부터 해당 로그인 유저의 친구를 찾아오는 로직 구현 및 타임리프, 페이징 수정
        Member member = (Member)session.getAttribute("user");

        Page<Friends> friends = friendService.findAllFriends(pageable, member.getId());


        if(friends!=null && friends.hasContent()){
            model.addAttribute("friends", friends);
        } else{
            model.addAttribute("friends", Collections.emptyList());
        }


        return "friends/friends";
    }

}
