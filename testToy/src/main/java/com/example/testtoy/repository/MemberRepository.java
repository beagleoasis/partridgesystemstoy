package com.example.testtoy.repository;

import com.example.testtoy.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
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

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name and m.state is null", Member.class)
                .setParameter("name",name)
                .getResultList();
    }

    public boolean delete(String name){
        // 삭제할 유저 찾기
        List<Member> findMember = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        // 삭제할 유저가 존재하는 경우,
        if(!findMember.isEmpty()){
            // 삭제된 유저 'd' 상태 처리
            findMember.get(0).setState("d");
            em.flush(); // 데이터베이스에 변경 내역 적용
            return true;
        }
        // 삭제할 유저가 존재하지 않는 경우,
        else{
            return false;
        }
    }
}
