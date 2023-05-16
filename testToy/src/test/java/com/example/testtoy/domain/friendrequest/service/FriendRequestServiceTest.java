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
public class FriendRequestServiceTest {

    @Autowired
    FriendRequestService friendRequestService;

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
    @DisplayName("FriendRequest-친구 요청 내역 조회")
    void testFindById(){

        //given
        Member sender = setUpMember("kjm1","123");
        Member receiver = setUpMember("kjm2","123");

        FriendRequest friendRequest = FriendRequest.createFriendRequest(sender,receiver, FriendStatus.REQUEST);

        friendRequestRepository.save(friendRequest);

        //when
        Optional<FriendRequest> foundFriendRequest = friendRequestService.findById(sender.getId(),receiver.getId());

        //then
        assertThat(foundFriendRequest).isNotEmpty();
        assertThat(foundFriendRequest.get().getSender().getId()).isEqualTo(sender.getId());

    }

    @Test
    @DisplayName("FriendRequest-친구 요청 생성")
    void testSave(){

        //given
        Member member1 = setUpMember("kjm1","123");
        Member member2 = setUpMember("kjm2","123");

        FriendRequest friendRequest = FriendRequest.createFriendRequest(member1,member2, FriendStatus.REQUEST);

        //when
        friendRequestService.save(friendRequest);

        //then
        Optional<FriendRequest> savedFriendRequest = friendRequestRepository.findById(friendRequest.getId());

        assertThat(savedFriendRequest).isNotEmpty();
        assertThat(savedFriendRequest.get().getSender().getId()).isEqualTo(member1.getId());

    }

}
