package com.example.testtoy.service;

import com.example.testtoy.domain.member.Member;
import com.example.testtoy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private PasswordEncoder passwordEncoder;

    MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder){
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
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
    public Long join(Member member){

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

    private boolean checkDuplicateMember(Member member){
        List<Member> findMember = memberRepository.findByName(member.getName());

        if(!findMember.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }

    public Member authenticate(String name, String password){

        // 사용자 이름으로 사용자 조회
        List<Member> member = memberRepository.findByName(name);

        // 사용자 일치
        if(!member.isEmpty() && passwordEncoder.matches(password, member.get(0).getPassword())){
            // 비밀번호 검증
            return member.get(0);
        }
        // 사용자 불일치
        else{
            return null;
        }

    }

}
