package com.example.testtoy.domain.commentlike.service;

import com.example.testtoy.domain.board.repository.BoardRepository;
import com.example.testtoy.domain.comment.repository.CommentRepository;
import com.example.testtoy.domain.commentlike.repository.CommentLikeRepository;
import com.example.testtoy.domain.member.repository.MemberRepository;
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
public class CommentLikeFacadeServiceTest {

    @Autowired
    CommentLikeService commentLikeService;

    @Autowired
    CommentLikeFacadeService commentLikeFacadeService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentLikeRepository commentLikeRepository;


    @Test
    @DisplayName("CommentLikeFacade-댓글의 좋아요/취소")
    void test(){

        //given


        //when


        //then


    }

}
