package com.example.testtoy.ctrl;

import com.example.testtoy.domain.member.Member;
import com.example.testtoy.dto.SaveMemberDto;
import com.example.testtoy.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.authentication.PasswordEncoderParser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;

@RequestMapping("members")
@Controller
public class MemberController {

    private final MemberService memberService;

    private PasswordEncoder passwordEncoder;

    MemberController(MemberService memberService, PasswordEncoder passwordEncoder){
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/new")
    public String getJoinForm(Model model){
        model.addAttribute("memberForm", new SaveMemberDto());

        return "members/create_join_form";
    }

    @PostMapping(value = "/new")
    public String saveMember(SaveMemberDto saveMemberDto){
        
        memberService.join(saveMemberDto);

        return "index";
    }

    @GetMapping(value = "/login")
    public String getLoginForm(){

        return "members/login";
    }

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

    @GetMapping("/logout")
    public String logout(HttpSession session){

        session.invalidate(); // 세션 무효화

        return "members/login";
    }


    @GetMapping("mypage")
    public String getMypage(){

        return "members/mypage";
    }

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
