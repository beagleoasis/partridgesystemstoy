package com.example.testtoy.domain.friend.repository;

import com.example.testtoy.domain.friend.domain.Friends;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FriendRepository extends JpaRepository<Friends, Long> {

    // member_id로 친구 목록 조회
    @Query("SELECT fr FROM Friends fr WHERE fr.friendMember.id = :member_id OR fr.member.id = :member_id")
    Page<Friends> findFriendsByMember_Id(Pageable pageable, Long member_id);


}
