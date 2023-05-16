package com.example.testtoy.domain.member.controller;

import com.example.testtoy.domain.member.domain.Member;
import com.example.testtoy.domain.member.domain.SaveMemberDto;
import com.example.testtoy.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequestMapping("members")
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
    *
    * @method : getJoinForm
    *
    * @explain : 회원가입 페이지 이동
    * @author : User
    * @date : 2023-05-09
    *
    **/
    @GetMapping(value = "/new")
    public String getJoinForm(Model model){
        model.addAttribute("memberForm", new SaveMemberDto());

        return "members/create_join_form";
    }

    /**
    *
    * @method : saveMember
    *
    * @explain : 회원가입 처리
    * @author : User
    * @date : 2023-05-09
    *
    **/
    @PostMapping(value = "/new")
    public String saveMember(SaveMemberDto saveMemberDto){

        memberService.join(saveMemberDto);

        return "index";
    }

    /**
    *
    * @method : getLoginForm
    *
    * @explain : 로그인 페이지 이동
    * @author : User
    * @date : 2023-05-09
    *
    **/
    @GetMapping(value = "/login")
    public String getLoginForm(){

        return "members/login";
    }

    /**
    *
    * @method : loginMember
    *
    * @explain : 로그인 처리
    * @author : User
    * @date : 2023-05-09
    *
    **/
    @PostMapping(value = "/login")
    public String loginMember(HttpServletRequest request, HttpSession session){

        String name = request.getParameter("name");
        String password = request.getParameter("password");

        // 유저 이름과 비밀번호를 통한 사용자 인증
        Member member = memberService.authenticate(name,password);

        if(member == null){
            // 인증 실패 시 로그인 페이지 이동
            return "members/login";
        }

        // 인증 성공 시, HttpSession에 사용자 정보 저장
        session.setAttribute("user", member);

        return "index";
    }

    /**
    *
    * @method : logout
    *
    * @explain : 로그아웃 처리
    * @author : User
    * @date : 2023-05-09
    *
    **/
    @GetMapping("/logout")
    public String logout(HttpSession session){

        session.invalidate(); // 세션 무효화

        return "members/login";
    }

    /**
    *
    * @method : getMypage
    *
    * @explain : 마이페이지 이동
    * @author : User
    * @date : 2023-05-09
    *
    **/
    @GetMapping("mypage")
    public String getMypage(){

        return "members/mypage";
    }

    /**
    *
    * @method : deleteMember
    *
    * @explain : 마이페이지-회원 탈퇴 처리
    * @author : User
    * @date : 2023-05-09
    *
    **/
    @PutMapping("deactive/{name}")
    public ResponseEntity deleteMember(@PathVariable String name, HttpServletRequest request, HttpSession session){

        // 탈퇴 회원의 상태값 변경
        if(memberService.deleteMember(name)){
            session.invalidate();
            return ResponseEntity.ok(201);
        }else{
            return ResponseEntity.ok(0);
        }

    }


}
