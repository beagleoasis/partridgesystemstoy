package com.example.testtoy.domain.board.service;

import com.example.testtoy.domain.board.domain.Board;
import com.example.testtoy.domain.board.repository.BoardRepository;
import com.example.testtoy.domain.member.domain.Member;
import com.example.testtoy.domain.member.repository.MemberRepository;
import com.example.testtoy.domain.member.service.MemberService;
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

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
    Member setUpMember(){

        String name = "kjm";
        String password = "123";

        Member member = Member.createMember(name,password);

        memberRepository.save(member);

        return member;
    }

    @Before
    Board setUpBoard(Long memberId, String name, String title, String content){
        Board board = Board.builder()
                .memberid(memberId)
                .name(name)
                .title(title)
                .content(content)
                .build();

        boardRepository.save(board);

        return board;
    }

    @Test
    @DisplayName("Board-게시판 생성")
    void testSave(){

        //given
        Member member = setUpMember();

        String boardTitle = "testTitle";
        String boardContent = "testContent";

        Board board = Board.builder()
                .memberid(member.getId())
                .name(member.getName())
                .title(boardTitle)
                .content(boardContent)
                .build();

        //when
        boardService.save(board);

        //then
        Optional<Board> foundBoard = boardRepository.findByIdAndStateIsNull(board.getId());

        assertThat(foundBoard).isNotEmpty();
        assertThat(foundBoard.get().getId()).isEqualTo(board.getId());

    }

    @Test
    @DisplayName("Board-단일 게시판 출력")
    void testFindByIdAndStateIsNull(){

        //given
        Member member = setUpMember();

        String boardTitle = "testTitle";
        String boardContent = "testContent";

        Board board = setUpBoard(member.getId(),member.getName(),boardTitle,boardContent);

        //when
        Board foundBoard = boardService.findByIdAndStateIsNull(board.getId());

        //then
        assertThat(foundBoard).isNotNull();
        assertThat(foundBoard.getId()).isEqualTo(board.getId());

    }

    @Test
    @DisplayName("Board-게시판 목록 출력")
    void testFindAllBoardsBySortType(){

        //given
        Member member = setUpMember();

        String boardTitle = "testTitle";
        String boardContent = "testContent";

        Board board = setUpBoard(member.getId(),member.getName(),boardTitle,boardContent);

        String sortType = "likeBoard";

        Pageable pageable = PageRequest.of(0,10);

        //when
        Page<Board> boards = boardService.findAllBoardsBySortType(pageable,sortType);

        //then
        assertThat(boards).isNotNull();
        assertThat(boards.getContent().size()).isLessThanOrEqualTo(10);

    }

    @Test
    @DisplayName("Board-게시글 삭제")
    void testDeleteBoard(){

        //given
        Member member = setUpMember();

        String boardTitle = "testTitle";
        String boardContent = "testContent";

        Board board = setUpBoard(member.getId(),member.getName(),boardTitle,boardContent);

        //when
        boardService.deleteBoard(board.getId());

        //then
        Board deletedBoard = boardRepository.findByIdAndStateIsNull(board.getId()).orElse(null);

        assertThat(deletedBoard).isNull();

    }

    @Test
    @DisplayName("Board-게시글 상세 페이지 조회")
    void testFindByIdAndIncreaseVisit(){

        //given
        Member member = setUpMember();

        String boardTitle = "testTitle";
        String boardContent = "testContent";

        Board board = setUpBoard(member.getId(),member.getName(),boardTitle,boardContent);

        int getBoardVisitCount = board.getVisit();

        //when
        Board foundBoard = boardService.findByIdAndIncreaseVisit(board.getId());

        //then
        assertThat(foundBoard.getName()).isEqualTo(member.getName());
        assertThat(foundBoard.getTitle()).isEqualTo(boardTitle);
        assertThat(getBoardVisitCount+Board.INCREMENT).isEqualTo(foundBoard.getVisit());

    }

    @Test
    @DisplayName("Board-게시글 id로 조회")
    void testFindById(){

        //given
        Member member = setUpMember();

        String boardTitle = "testTitle";
        String boardContent = "testContent";

        Board board = setUpBoard(member.getId(),member.getName(),boardTitle,boardContent);

        //when
        Board foundBoard = boardService.findById(board.getId());

        //then
        assertThat(foundBoard).isNotNull();
        assertThat(foundBoard.getTitle()).isEqualTo(boardTitle);
        assertThat(foundBoard.getName()).isEqualTo(member.getName());

    }

}
