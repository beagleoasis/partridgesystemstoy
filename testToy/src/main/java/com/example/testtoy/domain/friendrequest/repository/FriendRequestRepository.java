package com.example.testtoy.domain.friendrequest.repository;

import com.example.testtoy.domain.friendrequest.domain.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {



    @Query("SELECT fr FROM FriendRequest fr WHERE fr.receiver.id = :receiverId AND fr.sender.id = :senderId")
    Optional<FriendRequest> findById(Long senderId, Long receiverId);

}
