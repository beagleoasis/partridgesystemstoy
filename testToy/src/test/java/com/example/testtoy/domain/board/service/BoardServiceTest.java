package com.example.testtoy.domain.board.service;

import com.example.testtoy.domain.board.domain.Board;
import com.example.testtoy.domain.board.repository.BoardRepository;
import com.example.testtoy.domain.member.domain.Member;
import com.example.testtoy.domain.member.domain.SaveMemberDto;
import com.example.testtoy.domain.member.repository.MemberRepository;
import com.example.testtoy.domain.member.service.MemberService;
import com.example.testtoy.global.CustomException;
import com.example.testtoy.global.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    // 게시판 테스트를 위한 member 미리 생성
    @Before
    void setUpMember(){
        SaveMemberDto saveMemberDto = new SaveMemberDto();
        saveMemberDto.setName("kjm");
        saveMemberDto.setPassword("123");
        memberService.join(saveMemberDto);
    }

    @Test
    @DisplayName("Board-게시판 생성")
    void testSave(){

        //given
        setUpMember();
        Member member = memberRepository.findByName("kjm").orElseThrow();
        Board board = Board.builder()
                .memberid(member.getId())
                .name(member.getName())
                .title("test")
                .content("test")
                .build();

        //when
        boardService.save(board);

        Board getBoard = boardService.getBoard(board.getId());

        //then
        Assertions.assertThat(board).isEqualTo(getBoard); // 확인 필요

    }

    @Test
    @DisplayName("Board-단일 게시판 출력")
    void testGetBoard(){

        //given
        setUpMember();
        Member member = memberRepository.findByName("kjm")
                .orElseThrow(()->new CustomException(ErrorCode.Member_ID_NOT_FOUND));

        Board board = Board.builder()
                    .memberid(member.getId())
                    .name(member.getName())
                    .title("test")
                    .content("test")
                    .build();

        boardRepository.save(board);

        //when
        Board getBoard = boardService.getBoard(board.getId());

        //then
        Assertions.assertThat(board).isEqualTo(getBoard);

    }

    @Test
    @DisplayName("Board-게시판 목록 출력")
    void testFindAllBoardsBySortType(){

        //given
        String sortType = "likeBoard";

        Pageable pageable = PageRequest.of(0,10);

        //when
        Page<Board> boards = boardService.findAllBoardsBySortType(pageable,sortType);

        //then
        Assertions.assertThat(boards).isNotNull();
        Assertions.assertThat(boards).isNotEmpty();
        Assertions.assertThat(boards.getContent()).size().isLessThanOrEqualTo(10);

    }

    @Test
    @DisplayName("Board-게시글 삭제")
    void testDeleteBoard(){

        //given
        setUpMember();
        Member member = memberRepository.findByName("kjm")
                .orElseThrow(()->new CustomException(ErrorCode.Member_ID_NOT_FOUND));

        Board board = Board.builder()
                .memberid(member.getId())
                .name(member.getName())
                .title("test")
                .content("test")
                .build();

        boardRepository.save(board);

        //when
        boardService.deleteBoard(board.getId());

        //then
        Board deletedBoard = boardRepository.findByIdAndStateIsNull(board.getId()).orElse(null);

        Assertions.assertThat(deletedBoard).isNull();

    }

    @Test
    @DisplayName("Board-게시글 상세 페이지 조회")
    void testFindBoard(){

        //given
        setUpMember();
        Member member = memberRepository.findByName("kjm")
                .orElseThrow(()->new CustomException(ErrorCode.Member_ID_NOT_FOUND));

        Board board = Board.builder()
                .memberid(member.getId())
                .name(member.getName())
                .title("test")
                .content("test")
                .build();

        int getBoardVisitCount = board.getVisit();

        boardRepository.save(board);

        //when
        Board foundBoard = boardService.findBoard(board.getId());

        //then
        Assertions.assertThat(foundBoard.getName()).isEqualTo(member.getName());
        Assertions.assertThat(foundBoard.getTitle()).isEqualTo("test");
        Assertions.assertThat(getBoardVisitCount+Board.INCREMENT).isEqualTo(foundBoard.getVisit());

    }

    @Test
    @DisplayName("Board-게시글 id로 조회")
    void testFindById(){

        //given
        setUpMember();
        Member member = memberRepository.findByName("kjm")
                .orElseThrow(()->new CustomException(ErrorCode.Member_ID_NOT_FOUND));

        Board board = Board.builder()
                .memberid(member.getId())
                .name(member.getName())
                .title("test")
                .content("test")
                .build();

        boardRepository.save(board);

        //when
        Board foundBoard = boardService.findById(board.getId());

        //then
        Assertions.assertThat(foundBoard).isNotNull();
        Assertions.assertThat(foundBoard.getTitle()).isEqualTo("test");
        Assertions.assertThat(foundBoard.getName()).isEqualTo(member.getName());

    }

}
