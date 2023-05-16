package com.example.testtoy.domain.friendrequest.service;

import com.example.testtoy.domain.friend.domain.FriendStatus;
import com.example.testtoy.domain.friendrequest.domain.FriendRequest;
import com.example.testtoy.domain.friendrequest.repository.FriendRequestRepository;
import com.example.testtoy.domain.member.domain.Member;
import com.example.testtoy.domain.member.repository.MemberRepository;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class FriendRequestFacadeServiceTest {

    @Autowired
    FriendRequestFacadeService friendRequestFacadeService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    FriendRequestRepository friendRequestRepository;

    @Before
    Member setUpMember(String name, String password){
        Member member = Member.createMember(name,password);
        memberRepository.save(member);

        return member;
    }

    @Test
    @DisplayName("FriendRequestFacade-친구 요청 및 수락")
    void testSendFriendRequest(){

        //given
        Member sender = setUpMember("kjm1","123");
        Member receiver = setUpMember("kjm2","123");

        //when
        friendRequestFacadeService.sendFriendRequest(sender.getId(),receiver.getId());

        //then
        Optional<FriendRequest> friendRequest = friendRequestRepository.findById(sender.getId(),receiver.getId());
        assertThat(friendRequest).isNotEmpty();
        assertThat(friendRequest.get().getState()).isEqualTo(FriendStatus.REQUEST);

        //when
        friendRequestFacadeService.sendFriendRequest(receiver.getId(),sender.getId());

        //then
        friendRequest = friendRequestRepository.findById(receiver.getId(),sender.getId());
        assertThat(friendRequest).isNotEmpty();
        assertThat(friendRequest.get().getState()).isEqualTo(FriendStatus.FRIEND);

    }

}
