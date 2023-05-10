package com.example.testtoy.repository;

import com.example.testtoy.domain.friendrequest.FriendRequest;
import com.example.testtoy.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {



    @Query("SELECT fr FROM FriendRequest fr WHERE fr.receiver.id = :receiverId AND fr.sender.id = :senderId")
    FriendRequest existsFriendRequestsBySender_IdAndAndReceiver_Id(Long senderId, Long receiverId);

}
