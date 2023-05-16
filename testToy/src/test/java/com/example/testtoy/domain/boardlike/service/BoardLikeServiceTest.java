package com.example.testtoy.domain.boardlike.service;

import com.example.testtoy.domain.board.domain.Board;
import com.example.testtoy.domain.board.repository.BoardRepository;
import com.example.testtoy.domain.board.service.BoardService;
import com.example.testtoy.domain.boardlike.domain.BoardLike;
import com.example.testtoy.domain.boardlike.repository.BoardLikeRepository;
import com.example.testtoy.domain.member.domain.Member;
import com.example.testtoy.domain.member.domain.SaveMemberDto;
import com.example.testtoy.domain.member.repository.MemberRepository;
import com.example.testtoy.domain.member.service.MemberService;
import com.example.testtoy.global.CustomException;
import com.example.testtoy.global.exception.ErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BoardLikeServiceTest {

    @Autowired
    BoardLikeService boardLikeService;

    @Autowired
    MemberService memberService;

    @Autowired
    BoardService boardService;

    @Autowired
    BoardLikeRepository boardLikeRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Before
    Member setUpMember(String name, String password){
        Member member = Member.createMember(name,password);

        memberRepository.save(member);

        return member;
    }

    @Before
    Board setUpBoard(Long memberid){
        Board board = Board.builder()
                        .memberid(memberid)
                        .title("test")
                        .content("test")
                        .build();

        boardService.save(board);
        return board;
    }

    @Before
    BoardLike setUpBoardLike(Board board, Member member){
        BoardLike boardLike = BoardLike.builder()
                                .board(board)
                                .member(member)
                                .build();

        boardLikeRepository.save(boardLike);

        return boardLike;
    }

    @Test
    @DisplayName("BoardLike-게시글 좋아요 조회")
    void testFindBoardLikeByBoard_IdAndMember_Id(){

        //given
        Member member = setUpMember("kjm","123");

        Board board = setUpBoard(member.getId());

        BoardLike boardLike = setUpBoardLike(board,member);

        //when
        Optional<BoardLike> foundBoardLike = boardLikeService
                .findBoardLikeByBoard_IdAndMember_Id(board.getId(), member.getId());

        //then
        assertThat(foundBoardLike).isNotEmpty();
        assertThat(foundBoardLike).isNotNull();
        assertThat(foundBoardLike.get().getMember().getId()).isEqualTo(member.getId());
        assertThat(foundBoardLike.get().getBoard().getId()).isEqualTo(board.getId());

    }

    @Test
    @DisplayName("BoardLike-게시글 삭제")
    void testDeleteBoardLike(){

        //given
        Member member = setUpMember("kjm","123");

        Board board = setUpBoard(member.getId());

        BoardLike boardLike = setUpBoardLike(board,member);

        //when
        boardLikeService.deleteBoardLike(board.getId(),member.getId());

        //then
        Optional<BoardLike> deletedBoardLike = boardLikeRepository.findById(boardLike.getId());

        assertThat(deletedBoardLike).isEmpty();

    }


    @Test
    @DisplayName("BoardLike-게시글 좋아요 생성")
    void testSave(){

        //given
        Member member = setUpMember("kjm","123");

        Board board = setUpBoard(member.getId());

        BoardLike boardLike = BoardLike.createBoardLike(board,member);

        //when
        boardLikeService.save(boardLike);

        //then
        Optional<BoardLike> savedBoardLike = boardLikeRepository.findById(boardLike.getId());

        assertThat(savedBoardLike).isNotNull();
        assertThat(savedBoardLike.get().getBoard().getId()).isEqualTo(board.getId());
        assertThat(savedBoardLike.get().getMember().getId()).isEqualTo(member.getId());

    }

}
