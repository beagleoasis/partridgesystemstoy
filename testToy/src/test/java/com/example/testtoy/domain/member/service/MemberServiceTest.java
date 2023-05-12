package com.example.testtoy.domain.member.service;

import com.example.testtoy.domain.member.domain.Member;
import com.example.testtoy.domain.member.domain.SaveMemberDto;
import com.example.testtoy.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("FindOneById Test")
    //@DisplayName("Member-아이디로 member 객체를 찾는 경우")
    void testFindOneById(){

        //given
        Member member = Member.createMember("kjm","123");
        memberRepository.save(member);

        //when
        Member foundMember = memberService.findOneById(member.getId()).orElseThrow();

        //then
        assertNotNull(foundMember);
        assertEquals("kjm", foundMember.getName());
        assertEquals("123",foundMember.getPassword());

    }

    @Test
    @DisplayName("Join Test")
    void testJoin(){

        //given
        SaveMemberDto saveMemberDto = new SaveMemberDto();
        saveMemberDto.setName("kjm");
        saveMemberDto.setPassword("123");

        //when
        memberService.join(saveMemberDto);

        //then
        assertEquals(saveMemberDto.getName(),memberRepository.findByName("kjm").orElseThrow().getName());

    }


    @Test
    @DisplayName("CheckDuplicateMember Test")
    void testCheckDuplicateMember(){

        //given
        SaveMemberDto member1 = new SaveMemberDto();
        member1.setName("kjm");
        member1.setPassword("123");

        SaveMemberDto member2 = new SaveMemberDto();
        member2.setName("kjm");
        member2.setPassword("123");

        //when
        memberService.join(member1);

        try{
            memberService.join(member2);
        } catch (IllegalStateException e){
            return;
        }

        //then
        fail("예외 발생");

    }

    @Test
    @DisplayName("Authenticate Test")
    void testAuthenticate(){

        //given
        SaveMemberDto saveMemberDto = new SaveMemberDto();
        saveMemberDto.setName("kjm");
        saveMemberDto.setPassword("123");

        memberService.join(saveMemberDto);

        //when
        Member member = memberService.authenticate(saveMemberDto.getName(),saveMemberDto.getPassword());

        //then
        assertNotNull(member);

    }

    @Test
    @DisplayName("회원 탈퇴 테스트")
    void testDeleteMember(){

        //given
        SaveMemberDto saveMemberDto = new SaveMemberDto();
        saveMemberDto.setName("kjm");
        saveMemberDto.setPassword("123");

        memberService.join(saveMemberDto);

        //when
        boolean result = memberService.deleteMember(saveMemberDto.getName());

        //then
        assertTrue(result);

    }


}
