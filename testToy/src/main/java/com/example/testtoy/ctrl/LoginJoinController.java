package com.example.testtoy.ctrl;

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
    @GetMapping()
    public String loginJoinPage(){

        return "loginjoin/login_join";
    }


}
