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
    void setUpMember(){
        SaveMemberDto saveMemberDto = new SaveMemberDto();
        saveMemberDto.setName("kjm");
        saveMemberDto.setPassword("123");
        memberService.join(saveMemberDto);
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

    @Test
    @DisplayName("BoardLike-게시글 좋아요 조회")
    void testFindBoardLikeByBoard_IdAndMember_Id(){

        //given
        setUpMember();

        Member member = memberRepository.findByName("kjm")
                .orElseThrow(()->new CustomException(ErrorCode.Member_ID_NOT_FOUND));

        Board board = setUpBoard(member.getId());

        BoardLike boardLike = BoardLike.createBoardLike(board,member);

        boardLikeRepository.save(boardLike);

        //when
        BoardLike foundBoardLike = boardLikeService.findBoardLikeByBoard_IdAndMember_Id(board.getId(), member.getId())
                .orElseThrow(()->new CustomException(ErrorCode.BOARD_LIKE_ID_NOT_FOUND));

        //then
        Assertions.assertThat(foundBoardLike).isNotNull();
        Assertions.assertThat(foundBoardLike.getMember().getId()).isEqualTo(member.getId());
        Assertions.assertThat(foundBoardLike.getBoard().getId()).isEqualTo(board.getId());

    }

    @Test
    @DisplayName("BoardLike-게시글 삭제")
    void test(){

        //given
        setUpMember();

        Member member = memberRepository.findByName("kjm")
                .orElseThrow(()->new CustomException(ErrorCode.Member_ID_NOT_FOUND));

        Board board = setUpBoard(member.getId());

        BoardLike boardLike = BoardLike.createBoardLike(board,member);

        boardLikeRepository.save(boardLike);

        //when
        boardLikeService.deleteBoardLike(board.getId(),member.getId());

        //then
        Optional<BoardLike> deletedboardLike = boardLikeRepository.findById(boardLike.getId());
        Assertions.assertThat(deletedboardLike).isEmpty();

    }


    @Test
    @DisplayName("BoardLike-게시글 좋아요 생성")
    void testSave(){

        //given
        setUpMember();

        Member member = memberRepository.findByName("kjm")
                .orElseThrow(()->new CustomException(ErrorCode.Member_ID_NOT_FOUND));

        Board board = setUpBoard(member.getId());

        BoardLike boardLike = BoardLike.createBoardLike(board,member);

        //when
        boardLikeService.save(boardLike);

        //then
        BoardLike savedBoardLike = boardLikeRepository.findById(boardLike.getId())
                .orElseThrow(()->new CustomException(ErrorCode.BOARD_LIKE_ID_NOT_FOUND));

        Assertions.assertThat(savedBoardLike).isNotNull();
        Assertions.assertThat(savedBoardLike.getBoard().getId()).isEqualTo(board.getId());
        Assertions.assertThat(savedBoardLike.getMember().getId()).isEqualTo(member.getId());

    }

}
