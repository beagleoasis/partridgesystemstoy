package com.example.testtoy.ctrl;

import com.example.testtoy.domain.member.Member;
import com.example.testtoy.dto.SaveMemberDto;
import com.example.testtoy.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.authentication.PasswordEncoderParser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
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
    public String showJoinForm(Model model){
        model.addAttribute("memberForm", new SaveMemberDto());

        return "members/create_join_form";
    }

    @PostMapping(value = "/new")
    public String saveMember(SaveMemberDto saveMemberDto){

        System.out.println(saveMemberDto.getName() + " , " + saveMemberDto.getPassword());

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(saveMemberDto.getPassword());

        Member member = new Member();
        member.setName(saveMemberDto.getName());
        member.setPassword(encodedPassword);

        memberService.join(member);

        return "index";
    }

    @GetMapping(value = "/login")
    public String showLoginForm(){

        return "members/login";
    }

    @PostMapping(value = "/login")
    public String loginMember(HttpServletRequest request){

        String name = request.getParameter("name");
        String password = request.getParameter("password");
        System.out.println("name : " + name + " , password : " + password);

        // 유저 이름과 비밀번호를 통한 사용자 인증
        Member member = memberService.authenticate(name,password);

        System.out.println(member.getPassword());
        return "";
    }

}
