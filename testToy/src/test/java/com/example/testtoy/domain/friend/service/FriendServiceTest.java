package com.example.testtoy.domain.friend.service;

import com.example.testtoy.domain.friend.domain.FriendStatus;
import com.example.testtoy.domain.friend.domain.Friends;
import com.example.testtoy.domain.friend.repository.FriendRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class FriendServiceTest {

    @Autowired
    FriendService friendService;

    @Autowired
    FriendRepository friendRepository;

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
    @DisplayName("Friend-친구 생성")
    void testSave(){

        //given
        Member sender = setUpMember("kjm1","123");
        Member receiver = setUpMember("kjm2","123");

        Friends friends = Friends.createFriend(sender,receiver);

        //when
        friendService.save(friends);

        //then
        Optional<Friends> savedFriend = friendRepository.findById(friends.getId());

        assertThat(savedFriend).isNotEmpty();
        assertThat(savedFriend.get().getMember().getName()).isEqualTo("kjm1");
        assertThat(savedFriend.get().getFriendMember().getName()).isEqualTo("kjm2");

    }

    @Test
    @DisplayName("Friend-친구 목록 조회")
    void testFindById(){

        //given
        Member sender = setUpMember("kjm1","123");
        Member receiver = setUpMember("kjm2","123");

        Friends friends = Friends.createFriend(sender,receiver);

        friendRepository.save(friends);

        Pageable pageable = PageRequest.of(0,10);

        //when
        Page<Friends> foundFriends = friendService.findById(pageable,sender.getId());

        //then
        assertThat(foundFriends).isNotNull();
        assertThat(foundFriends.stream().anyMatch(f -> f.getMember().equals(sender))).isTrue();
        assertThat(foundFriends.stream().anyMatch(f -> f.getFriendMember().equals(receiver))).isTrue();

    }

}
