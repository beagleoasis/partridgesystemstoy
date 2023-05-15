package com.example.testtoy.domain.member.service;

import com.example.testtoy.domain.member.domain.Member;
import com.example.testtoy.domain.member.domain.SaveMemberDto;
import com.example.testtoy.domain.member.repository.MemberRepository;
import com.example.testtoy.global.CustomException;
import com.example.testtoy.global.exception.ErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

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
        String name = "kjm";
        String password = "123";

        Member member = Member.createMember(name,password);
        memberRepository.save(member);

        //when
        Member foundMember = memberService.findOneById(member.getId())
                .orElseThrow(()->new CustomException(ErrorCode.Member_ID_NOT_FOUND));

        //then
        Assertions.assertThat(foundMember).isNotNull();
        Assertions.assertThat(name).isEqualTo(foundMember.getName());
        Assertions.assertThat(password).isEqualTo(foundMember.getPassword());

    }

    @Test
    @DisplayName("Join Test")
    void testJoin(){

        //given
        String name = "kjm";
        String password = "123";

        SaveMemberDto saveMemberDto = new SaveMemberDto();
        saveMemberDto.setName(name);
        saveMemberDto.setPassword(password);

        //when
        memberService.join(saveMemberDto);

        //then
        Assertions.assertThat(saveMemberDto.getName()).isEqualTo(memberRepository.findByName(name)
                .orElseThrow(()->new CustomException(ErrorCode.Member_ID_NOT_FOUND)));
    }


    @Test
    @DisplayName("CheckDuplicateMember Test")
    void testCheckDuplicateMember(){

        //given
        String name = "kjm";
        String password = "123";

        SaveMemberDto member1 = new SaveMemberDto();
        member1.setName(name);
        member1.setPassword(password);

        SaveMemberDto member2 = new SaveMemberDto();
        member2.setName(name);
        member2.setPassword(password);

        //when
        memberService.join(member1);

        try{
            memberService.join(member2);
        } catch (IllegalStateException e){
            return;
        }

        //then
        Assertions.fail("예외 발생");

    }

    @Test
    @DisplayName("Authenticate Test")
    void testAuthenticate(){

        //given
        String name = "kjm";
        String password = "123";

        SaveMemberDto saveMemberDto = new SaveMemberDto();
        saveMemberDto.setName(name);
        saveMemberDto.setPassword(password);

        memberService.join(saveMemberDto);

        //when
        Member member = memberService.authenticate(saveMemberDto.getName(),saveMemberDto.getPassword());

        //then
        Assertions.assertThat(member).isNotNull();

    }

    @Test
    @DisplayName("회원 탈퇴 테스트")
    void testDeleteMember(){

        //given
        String name = "kjm";
        String password = "123";

        SaveMemberDto saveMemberDto = new SaveMemberDto();
        saveMemberDto.setName(name);
        saveMemberDto.setPassword(password);

        memberService.join(saveMemberDto);

        //when
        boolean result = memberService.deleteMember(saveMemberDto.getName());

        //then
        Assertions.assertThat(result).isTrue();

    }


}
