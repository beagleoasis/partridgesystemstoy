package com.example.testtoy.domain.commentlike.service;

import com.example.testtoy.domain.board.domain.Board;
import com.example.testtoy.domain.board.repository.BoardRepository;
import com.example.testtoy.domain.comment.domain.Comment;
import com.example.testtoy.domain.comment.repository.CommentRepository;
import com.example.testtoy.domain.commentlike.domain.CommentLike;
import com.example.testtoy.domain.commentlike.domain.SaveOrDeleteCommentLikeDto;
import com.example.testtoy.domain.commentlike.repository.CommentLikeRepository;
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
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CommentLikeFacadeServiceTest {

    @Autowired
    CommentLikeService commentLikeService;

    @Autowired
    CommentLikeFacadeService commentLikeFacadeService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentLikeRepository commentLikeRepository;

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
    @DisplayName("CommentLikeFacade-댓글의 좋아요/취소")
    void testLikeOrUnlikeComment(){

        //given
        Member member = setUpMember();

        Board board = setUpBoard(member.getId());

        Comment comment = setUpComment(board,member.getId(),member.getName());

        SaveOrDeleteCommentLikeDto saveOrDeleteCommentLikeDto = new SaveOrDeleteCommentLikeDto();
        saveOrDeleteCommentLikeDto.setCommentid(comment.getId());
        saveOrDeleteCommentLikeDto.setMemberid(member.getId());

        //when
        commentLikeFacadeService.likeOrUnlikeComment(saveOrDeleteCommentLikeDto);

        //then
        Optional<CommentLike> foundCommentLike = commentLikeRepository.findCommentLikeByComment_IdAndMember_Id(comment.getId(),member.getId());
        Assertions.assertThat(foundCommentLike).isNotEmpty();

        //when
        commentLikeFacadeService.likeOrUnlikeComment(saveOrDeleteCommentLikeDto);

        //then
        foundCommentLike = commentLikeRepository.findCommentLikeByComment_IdAndMember_Id(comment.getId(),member.getId());
        Assertions.assertThat(foundCommentLike).isEmpty();
    }

}
