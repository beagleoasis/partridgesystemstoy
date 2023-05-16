package com.example.testtoy.domain.commentlike.service;

import com.example.testtoy.domain.board.domain.Board;
import com.example.testtoy.domain.board.repository.BoardRepository;
import com.example.testtoy.domain.comment.domain.Comment;
import com.example.testtoy.domain.comment.repository.CommentRepository;
import com.example.testtoy.domain.commentlike.domain.CommentLike;
import com.example.testtoy.domain.commentlike.repository.CommentLikeRepository;
import com.example.testtoy.domain.member.domain.Member;
import com.example.testtoy.domain.member.repository.MemberRepository;
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
public class CommentLikeServiceTest {

    @Autowired
    CommentLikeService commentLikeService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentLikeRepository commentLikeRepository;

    @Before
    Member setUpMember(String name, String password){
        Member member = Member.createMember(name,password);

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

    @Before
    CommentLike setupCommentLike(Member member, Comment comment){
        CommentLike commentLike = CommentLike.builder()
                .member(member)
                .comment(comment)
                .build();

        commentLikeRepository.save(commentLike);

        return commentLike;
    }


    @Test
    @DisplayName("CommentLike-댓글 좋아요 조회")
    void testFindCommentLikeByComment_IdAndMember_Id(){

        //given
        Member member = setUpMember("kjm","123");

        Board board = setUpBoard(member.getId());

        Comment comment = setUpComment(board,member.getId(),member.getName());

        CommentLike commentLike = setupCommentLike(member,comment);

        //when
        Optional<CommentLike> foundCommentLike =
                commentLikeService.findCommentLikeByComment_IdAndMember_Id(comment.getId(), member.getId());

        //then
        assertThat(foundCommentLike).isNotNull();
        assertThat(foundCommentLike.get().getMember().getId()).isEqualTo(member.getId());

    }

    @Test
    @DisplayName("CommentLike-댓글 좋아요 삭제")
    void testDeleteCommentLikeByCommentIdAndMemberId(){

        //given
        Member member = setUpMember("kjm","123");

        Board board = setUpBoard(member.getId());

        Comment comment = setUpComment(board,member.getId(),member.getName());

        CommentLike commentLike = setupCommentLike(member,comment);

        //when
        commentLikeService.deleteCommentLikeByCommentIdAndMemberId(comment.getId(),member.getId());

        //then
        Optional<CommentLike> foundCommentLike = commentLikeRepository
                .findCommentLikeByComment_IdAndMember_Id(comment.getId(),member.getId());

        assertThat(foundCommentLike).isEmpty();

    }

    @Test
    @DisplayName("CommentLike-댓글 좋아요 저장")
    void testSave(){

        //given
        Member member = setUpMember("kjm","123");

        Board board = setUpBoard(member.getId());

        Comment comment = setUpComment(board,member.getId(),member.getName());

        CommentLike commentLike = CommentLike.createCommentLike(comment,member);

        //when
        commentLikeService.save(commentLike);

        //then
        Optional<CommentLike> savedCommentLike = commentLikeRepository
                .findCommentLikeByComment_IdAndMember_Id(comment.getId(),member.getId());
        assertThat(savedCommentLike).isNotEmpty();

    }
}
