package com.example.testtoy.domain.member.service;

import com.example.testtoy.domain.member.domain.Member;
import com.example.testtoy.domain.member.domain.SaveMemberDto;
import com.example.testtoy.domain.member.repository.MemberRepository;
import com.example.testtoy.global.CustomException;
import com.example.testtoy.global.exception.ErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

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

    @Before
    Member setUpMember(String name, String password){
        Member member = Member.createMember(name,password);

        memberRepository.save(member);

        return member;
    }

    @Test
    @DisplayName("Member-아이디로 찾기")
    void testFindById(){

        //given
        String name = "kjm";
        String password = "123";

        Member member = setUpMember(name,password);

        //when
        Member foundMember = memberService.findById(member.getId());

        //then
        assertThat(foundMember).isNotNull();
        assertThat(name).isEqualTo(foundMember.getName());
        assertThat(password).isEqualTo(foundMember.getPassword());

    }

    @Test
    @DisplayName("Member-회원가입")
    void testJoin(){

        //given
        String name = "kjm";
        String password = "123";

        SaveMemberDto saveMemberDto = new SaveMemberDto();
        saveMemberDto.setName(name);
        saveMemberDto.setPassword(password);

        //when
        Long memberId = memberService.join(saveMemberDto);

        //then
        Optional<Member> member = memberRepository.findByName(name);

        assertThat(member).isNotEmpty();
        assertThat(member.get().getId()).isEqualTo(memberId);
    }


    @Test
    @DisplayName("Member-중복회원 확인")
    void testCheckDuplicateMember(){

        //given
        String name = "kjm";
        String password = "123";

        Member member1 = setUpMember(name,password);

        SaveMemberDto member2 = new SaveMemberDto();
        member2.setName(name);
        member2.setPassword(password);

        //when

        try{
            memberService.join(member2);
        } catch (IllegalStateException e){
            return;
        }

        //then
        fail("예외 발생");

    }

    @Test
    @DisplayName("Member-멤버 인증")
    void testAuthenticate(){

        //given
        String name = "kjm";
        String password = "123";

        SaveMemberDto saveMemberDto = new SaveMemberDto();
        saveMemberDto.setName(name);
        saveMemberDto.setPassword(password);

        memberService.join(saveMemberDto); // 비밀번호 암호화 처리 때문에 memberService 사용

        //when
        Member member = memberService.authenticate(saveMemberDto.getName(),saveMemberDto.getPassword());

        //then
        assertThat(member).isNotNull();

    }

    @Test
    @DisplayName("Member-회원 탈퇴")
    void testDeleteMember(){

        //given
        String name = "kjm";
        String password = "123";

        Member member = setUpMember(name,password);

        //when
        boolean result = memberService.deleteMember(member.getName());

        //then
        assertThat(result).isTrue();

    }


}
