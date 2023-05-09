package com.example.testtoy.service;

import com.example.testtoy.domain.member.Member;
import com.example.testtoy.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // 스프링과 함께 테스트
@SpringBootTest // 스프링부트를 띄운 상태로 테스트
@Transactional // 롤백 가능하도록
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception{
        //given
        //Member member = new Member();
        //member.setName("Kim");

        //when
        //Long saveId = memberService.join(member);

        //then
        //assertEquals(member, memberRepository.findOne(saveId));

    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        //Member member1 = new Member();
        //member1.setName("kim");

        //Member member2 = new Member();
        //member2.setName("kim");

        //when
        //memberService.join(member1);
        try{
            //memberService.join(member2); // 예외발생 지점
        } catch (IllegalStateException e){
            return;
        }

        //then
        fail("예외 발생"); // 코드가 도달하면 안 되는 지점

    }

}