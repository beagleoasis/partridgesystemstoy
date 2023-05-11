package com.example.testtoy.domain.friendrequest.repository;

import com.example.testtoy.domain.friendrequest.domain.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {



    @Query("SELECT fr FROM FriendRequest fr WHERE fr.receiver.id = :receiverId AND fr.sender.id = :senderId")
    FriendRequest existsFriendRequestsBySender_IdAndAndReceiver_Id(Long senderId, Long receiverId);

}
