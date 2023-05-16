package com.example.testtoy.domain.comment.service;

import com.example.testtoy.domain.board.domain.Board;
import com.example.testtoy.domain.board.repository.BoardRepository;
import com.example.testtoy.domain.comment.domain.Comment;
import com.example.testtoy.domain.comment.domain.SaveCommentDto;
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
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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

        String name = "kjm";
        String password = "123";

        Member member = Member.createMember(name,password);

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
    void testFindById(){

        //given
        Member member = setUpMember();

        Board board = setUpBoard(member.getId());

        Comment comment = setUpComment(board,member.getId(), member.getName());

        //when
        Comment foundComment = commentService.findById(comment.getId());

        //then
        assertThat(foundComment).isNotNull();
        assertThat(foundComment.getId()).isEqualTo(comment.getId());

    }

    @Test
    @DisplayName("Comment-댓글 리스트 조회")
    void testFindByBoardIdAndStateIsNull(){

        //given
        Member member = setUpMember();

        Board board = setUpBoard(member.getId());

        Comment comment = setUpComment(board,member.getId(),member.getName());

        //when
        List<Comment> commentList = commentService.findByBoardIdAndStateIsNull(board.getId());

        //then
        assertThat(commentList).isNotNull();
        assertThat(commentList).matches(list -> list.stream().anyMatch(c -> c.equals(comment)));

    }

    @Test
    @DisplayName("Comment-댓글 저장")
    void testSave(){

        //given
        Member member = setUpMember();

        Board board = setUpBoard(member.getId());

        String commentContent = "test";

        SaveCommentDto saveCommentDto = new SaveCommentDto();
        saveCommentDto.setMemberid(member.getId());
        saveCommentDto.setName(member.getName());
        saveCommentDto.setBoard_id(board.getId());
        saveCommentDto.setContent(commentContent);

        //when
        Comment comment = commentService.save(saveCommentDto);

        //then
        assertThat(comment).isNotNull();
        assertThat(comment.getMemberid()).isEqualTo(member.getId());

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
        Optional<Comment> foundComment = commentRepository.findById(comment.getId());

        assertThat(foundComment).isNotNull();
        assertThat(foundComment.get().getState()).isEqualTo("d");

    }


}
