package com.example.testtoy.domain.boardlike.service;

import com.example.testtoy.domain.board.domain.Board;
import com.example.testtoy.domain.board.repository.BoardRepository;
import com.example.testtoy.domain.board.service.BoardService;
import com.example.testtoy.domain.boardlike.domain.SaveOrDeleteBoardLikeDto;
import com.example.testtoy.domain.member.domain.Member;
import com.example.testtoy.domain.member.repository.MemberRepository;
import com.example.testtoy.domain.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BoardLikeFacadeServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    MemberService memberService;

    @Autowired
    BoardLikeService boardLikeService;

    @Autowired
    BoardLikeFacadeService boardLikeFacadeService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Before
    Member setUpMember(){

        String name = "kjm";
        String password = "123";

        Member member = Member.builder()
                        .name(name)
                        .password(password)
                        .build();

        memberRepository.save(member);

        return member;
    }

    @Before
    Board setUpBoard(Long memberId){
        String boardTitle = "testTitle";
        String boardContent = "testContent";

        Board board = Board.builder()
                .memberid(memberId)
                .title(boardTitle)
                .content(boardContent)
                .likes(0)
                .build();

        boardRepository.save(board);

        return board;
    }



    @Test
    @DisplayName("BoardLikeFacade-게시글 좋아요/취소")
    void testLikeOrUnlikeBoard(){

        //given
        Member member = setUpMember();
        Board board = setUpBoard(member.getId());

        SaveOrDeleteBoardLikeDto saveOrDeleteBoardLikeDto = new SaveOrDeleteBoardLikeDto();
        saveOrDeleteBoardLikeDto.setBoardid(board.getId());
        saveOrDeleteBoardLikeDto.setMemberid(member.getId());

        //when
        ResponseEntity responseEntity = boardLikeFacadeService.likeOrUnlikeBoard(saveOrDeleteBoardLikeDto);

        //then
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);

        //when
        responseEntity = boardLikeFacadeService.likeOrUnlikeBoard(saveOrDeleteBoardLikeDto);

        //then
        Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);

    }

}
