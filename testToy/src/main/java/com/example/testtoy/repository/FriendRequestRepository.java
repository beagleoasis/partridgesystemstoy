package com.example.testtoy.repository;

import com.example.testtoy.domain.friendrequest.FriendRequest;
import com.example.testtoy.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    Optional<FriendRequest> findBySenderAndReceiver(Member sender, Member receiver);
    boolean existsBySenderAndReceiver(Member sender, Member receiver);

}
