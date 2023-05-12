package com.example.testtoy.domain.board.service;

import com.example.testtoy.domain.board.domain.Board;
import com.example.testtoy.domain.board.repository.BoardRepository;
import com.example.testtoy.domain.member.domain.Member;
import com.example.testtoy.domain.member.domain.SaveMemberDto;
import com.example.testtoy.domain.member.repository.MemberRepository;
import com.example.testtoy.domain.member.service.MemberService;
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

import static org.junit.Assert.*;

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
                .memberid(String.valueOf(member.getId()))
                .name(member.getName())
                .title("test")
                .content("test")
                .build();

        //when
        boardService.save(board);

        Board getBoard = boardService.getBoard(board.getId()).orElseThrow();

        //then
        assertEquals(board.getTitle(),getBoard.getTitle());
        assertEquals(board.getContent(),getBoard.getContent());
    }

    @Test
    @DisplayName("Board-단일 게시판 출력")
    void testGetBoard(){

        //given
        setUpMember();
        Member member = memberRepository.findByName("kjm").orElseThrow();

        Board board = Board.builder()
                    .memberid(String.valueOf(member.getId()))
                    .name(member.getName())
                    .title("test")
                    .content("test")
                    .build();

        boardRepository.save(board);

        //when
        Board getBoard = boardService.getBoard(board.getId()).orElseThrow();

        //then
        assertEquals(board,getBoard);

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
        assertNotNull(boards);
        assertFalse(boards.isEmpty());
        assertTrue(boards.getContent().size()<=10);

    }

    @Test
    @DisplayName("Board-게시글 삭제")
    void testDeleteBoard(){

        //given
        setUpMember();
        Member member = memberRepository.findByName("kjm").orElseThrow();

        Board board = Board.builder()
                .memberid(String.valueOf(member.getId()))
                .name(member.getName())
                .title("test")
                .content("test")
                .build();

        boardRepository.save(board);

        //when
        boardService.deleteBoard(board.getId());

        //then
        Board deletedBoard = boardRepository.findByIdAndStateIsNull(board.getId()).orElse(null);

        assertNull(deletedBoard);
    }

}
