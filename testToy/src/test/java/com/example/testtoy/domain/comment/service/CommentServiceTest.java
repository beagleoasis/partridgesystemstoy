package com.example.testtoy.domain.comment.service;

import com.example.testtoy.domain.board.domain.Board;
import com.example.testtoy.domain.board.repository.BoardRepository;
import com.example.testtoy.domain.comment.domain.Comment;
import com.example.testtoy.domain.comment.repository.CommentRepository;
import com.example.testtoy.domain.member.domain.Member;
import com.example.testtoy.domain.member.repository.MemberRepository;
import org.junit.Before;
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
public class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    CommentRepository commentRepository;

    @Before
    Member setUpMember(){

        Member member = Member.builder()
                .name("kjm")
                .password("123")
                .build();

        return member;
    }

    @Before
    Board setUpBoard(Long memberId){
        Board board = Board.builder()
                .memberid(memberId)
                .title("test")
                .content("test")
                .build();

        return board;
    }

    @Before
    Comment setUpComment(Board board, Long memberId){
        Comment comment = Comment.builder()
                .board(board)
                .memberid(memberId)
                .content("testComment")
                .build();

        return comment;
    }

    @Test
    @DisplayName("Comment-댓글 가져오기")
    void testGetCommentById(){

        //given
        Member member = setUpMember();

        Board board = setUpBoard(member.getId());

        Comment comment = setUpComment(board,member.getId());


        //when


        //then


    }

}
