package com.example.testtoy.domain.comment.service;

import com.example.testtoy.domain.board.domain.Board;
import com.example.testtoy.domain.board.repository.BoardRepository;
import com.example.testtoy.domain.comment.domain.Comment;
import com.example.testtoy.domain.comment.repository.CommentRepository;
import com.example.testtoy.domain.member.domain.Member;
import com.example.testtoy.domain.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

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

        memberRepository.save(member);

        return member;
    }

    @Before
    Board setUpBoard(Long memberId){
        Board board = Board.builder()
                .memberid(memberId)
                .title("test")
                .content("test")
                .build();

        boardRepository.save(board);

        return board;
    }

    @Before
    Comment setUpComment(Board board, Long memberId, String memberName){
        Comment comment = Comment.builder()
                .board(board)
                .memberid(memberId)
                .name(memberName)
                .content("testComment")
                .build();

        commentRepository.save(comment);

        return comment;
    }

    @Test
    @DisplayName("Comment-댓글 조회")
    void testGetCommentById(){

        //given
        Member member = setUpMember();

        Board board = setUpBoard(member.getId());

        Comment comment = setUpComment(board,member.getId(), member.getName());

        //when
        Comment foundComment = commentService.getCommentById(comment.getId());

        //then
        Assertions.assertThat(foundComment.getId()).isEqualTo(comment.getId());

    }

    @Test
    @DisplayName("Comment-댓글 리스트 조회")
    void testGetComments(){

        //given
        Member member = setUpMember();

        Board board = setUpBoard(member.getId());

        Comment comment = setUpComment(board,member.getId(),member.getName());

        //when
        List<Comment> commentList = commentRepository.findByBoardIdAndStateIsNull(board.getId());

        //then
        Assertions.assertThat(commentList).isNotEmpty();
        Assertions.assertThat(commentList).anyMatch(comment1 -> comment1.getBoard().getId()==board.getId());

    }

    @Test
    @DisplayName("Comment-댓글 저장")
    void testSave(){

        //given
        Member member = setUpMember();

        Board board = setUpBoard(member.getId());

        //when
        Comment comment = setUpComment(board, member.getId(), member.getName());

        //then
        Assertions.assertThat(comment).isNotNull();
        Assertions.assertThat(comment.getMemberid()).isEqualTo(member.getId());

    }

    @Test
    @DisplayName("Comment-댓글 삭제")
    void testDeleteComment(){

        //given
        Member member = setUpMember();

        Board board = setUpBoard(member.getId());

        Comment comment = setUpComment(board, member.getId(), member.getName());

        //when
        commentService.deleteComment(comment.getId());

        //then
        Comment foundComment = commentRepository.findById(comment.getId()).orElse(null);

        Assertions.assertThat(foundComment).isNotNull();
        Assertions.assertThat(foundComment.getState()).isEqualTo("d");

    }


}
