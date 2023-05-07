package com.example.testtoy.repository;

import com.example.testtoy.domain.friend.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {

}
