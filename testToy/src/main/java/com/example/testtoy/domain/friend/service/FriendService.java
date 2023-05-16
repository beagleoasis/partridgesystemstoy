package com.example.testtoy.domain.friend.service;

import com.example.testtoy.domain.friend.domain.Friends;
import com.example.testtoy.domain.friend.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;

    /**
    *
    * @method : save
    *
    * @explain : 친구 관계 생성
    * @author : User
    * @date : 2023-05-11
    *
    **/
    public void save(Friends friends){
        friendRepository.save(friends);
    }

    /**
    *
    * @method : findById
    *
    * @explain : 친구 목록 조회
    * @author : User
    * @date : 2023-05-11
    *
    **/
    public Page<Friends> findById(Pageable pageable, Long memberId){

        return friendRepository.findById(pageable, memberId);
    }


}
