package com.example.testtoy.domain.member.repository;

import com.example.testtoy.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
    *
    * @method : findById
    *
    * @explain : 멤버 아이디로 조회
    * @author : User
    * @date : 2023-05-11
    *
    **/
    Optional<Member> findById(Long memberId);


    /**
    *
    * @method : findByName
    *
    * @explain : 멤버이름으로 멤버 리스트 조회
    * @author : User
    * @date : 2023-05-09
    *
    **/

    List<Member> findByName(String name);


}
