package com.example.testtoy.domain.member.service;

import com.example.testtoy.domain.member.domain.Member;
import com.example.testtoy.domain.member.domain.SaveMemberDto;
import com.example.testtoy.domain.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    private PasswordEncoder passwordEncoder;

    // 필드 주입 방식으로 의존성을 주입 받는 경우 값을 못가지고 오는 문제 발생
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
    *
    * @method : findOneById
    *
    * @explain : 멤버 단일 조회
    * @author : User
    * @date : 2023-05-11
    *
    **/
    @Transactional
    public Optional<Member> findOneById(Long memberId){
        return memberRepository.findById(memberId);
    }

    /**
    *
    * @method : join
    *
    * @explain : 회원가입
    * @author : User
    * @date : 2023-05-05
    *
    **/
    @Transactional
    public Long join(SaveMemberDto saveMemberDto){

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(saveMemberDto.getPassword());

        Member member = Member.createMember(saveMemberDto.getName(),encodedPassword);

        // 기존 가입 회원이 아닌 경우,
        if(checkDuplicateMember(member)){
            memberRepository.save(member);
        }
        // 기존 가입 회원인 경우,
        else{
            System.out.println("기존 회원");
        }
        return member.getId();
    }

    /**
    *
    * @method : checkDuplicateMember
    *
    * @explain : 중복 유저 확인
    * @author : User
    * @date : 2023-05-09
    *
    **/
    private boolean checkDuplicateMember(Member member){
        List<Member> findMember = memberRepository.findByName(member.getName());

        if(!findMember.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }

    /**
    *
    * @method : authenticate
    *
    * @explain : 유저 인증
    * @author : User
    * @date : 2023-05-09
    *
    **/
    public Member authenticate(String name, String password){

        // 사용자 이름으로 사용자 조회
        List<Member> member = memberRepository.findByName(name);

        // 사용자 일치
        if(!member.isEmpty()){
            // 비밀번호 검증
            if(passwordEncoder.matches(password, member.get(0).getPassword())){
                return member.get(0);
            } else{
                return null;
            }
        }
        // 사용자 불일치
        else{
            return null;
        }

    }

    /**
    *
    * @method : deleteMember
    *
    * @explain : 유저 삭제 
    * @author : User
    * @date : 2023-05-09
    *
    **/
    @Transactional
    public boolean deleteMember(String name){
        // 멤버 리스트 조회
        List<Member> members = memberRepository.findByName(name);

        if(!members.isEmpty()){
            // 회원 탈퇴 처리
            members.get(0).updateMemberState("d");
            return true;
        }
        // 삭제 실패
        else{

            return false;
        }
    }


}
