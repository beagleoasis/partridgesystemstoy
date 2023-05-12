package com.example.testtoy.domain.commentlike.service;

import com.example.testtoy.domain.comment.domain.Comment;
import com.example.testtoy.domain.comment.service.CommentService;
import com.example.testtoy.domain.commentlike.domain.CommentLike;
import com.example.testtoy.domain.member.domain.Member;
import com.example.testtoy.domain.member.service.MemberService;
import com.example.testtoy.domain.commentlike.domain.SaveOrDeleteCommentLikeDto;
import com.example.testtoy.global.CustomException;
import com.example.testtoy.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class CommentLikeFacadeService {

    private final CommentService commentService;

    private final CommentLikeService commentLikeService;

    private final MemberService memberService;

    /**
     *
     * @method : likeOrUnlikeComment
     *
     * @explain : 댓글의 좋아요/좋아요 취소
     * @author : User
     * @date : 2023-05-09
     *
     **/
    public ResponseEntity likeOrUnlikeComment(SaveOrDeleteCommentLikeDto saveOrDeleteCommentLikeDto){

        Long commentId = saveOrDeleteCommentLikeDto.getCommentid();
        Long memberId = saveOrDeleteCommentLikeDto.getMemberid();

        // 댓글 존재 여부 확인
        Comment comment = commentService.getCommentById(commentId);

        // 댓글 좋아요 존재 여부 확인
        Optional<CommentLike> commentLike = commentLikeService.getCommentLikeByCommentIdAndMemberId(commentId,memberId);


        // 댓글 좋아요가 존재한다면,
        if(commentLike.isPresent()){
            // 댓글 좋아요 -1 카운트
            comment.decreaseCommentLikes();
            commentLikeService.deleteCommentLikeByCommentIdAndMemberId(commentId,memberId);

            return ResponseEntity.ok(204);
        }
        // 댓글 좋아요가 존재하지 않는다면,
        else {
            Member member = memberService.findOneById(memberId)
                    .orElseThrow(()->new CustomException(ErrorCode.ID_NOT_FOUND));
            // 댓글 좋아요 +1 카운트
            comment.increaseCommentLikes();
            // 댓글 좋아요
            CommentLike saveCommentLike = CommentLike.createCommentLike(comment,member);

            commentLikeService.save(saveCommentLike);

            return ResponseEntity.ok(201);
        }

    }

}
