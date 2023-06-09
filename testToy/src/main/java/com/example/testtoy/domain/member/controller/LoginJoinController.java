package com.example.testtoy.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/loginJoin")
@Controller
public class LoginJoinController {

    /**
    *
    * @method : loginJoinPage
    *
    * @explain : 로그인/회원가입 페이지 이동
    * @author : User
    * @date : 2023-05-04
    *
    **/
    @Operation(summary = "로그인/회원가입 페이지", description = "로그인/회원가입 페이지로 이동합니다.")
    @GetMapping()
    public String loginJoinPage(){

        return "loginjoin/login_join";
    }


}
