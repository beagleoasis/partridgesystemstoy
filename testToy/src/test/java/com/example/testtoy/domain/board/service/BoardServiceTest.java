package com.example.testtoy.domain.board.service;

import com.example.testtoy.domain.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    MemberService memberService;


    @Test
    @DisplayName("Board-게시판 목록 출력")
    void testGetBoard(){
        
        //given


        //when


        //then


    }


}
