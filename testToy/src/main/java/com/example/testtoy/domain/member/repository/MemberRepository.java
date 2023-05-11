package com.example.testtoy.domain.member.repository;

import com.example.testtoy.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class MemberRepository {

    @PersistenceContext
    private final EntityManager em;

    /**
    *
    * @method : save
    *
    * @explain : member 생성
    * @author : User
    * @date : 2023-05-05
    *
    **/
    public void save(Member member){
        em.persist(member);
    }

    /**
    *
    * @method : findOne
    *
    * @explain : 멤버 id로 조회
    * @author : User
    * @date : 2023-05-09
    *
    **/
    public Member findOneById(Long id){
        return em.find(Member.class, id);
    }

    /**
    *
    * @method : findByName
    *
    * @explain : 멤버이름으로 멤버 리스트 조회
    * @author : User
    * @date : 2023-05-09
    *
    **/
    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name and m.state is null", Member.class)
                .setParameter("name",name)
                .getResultList();
    }

}
