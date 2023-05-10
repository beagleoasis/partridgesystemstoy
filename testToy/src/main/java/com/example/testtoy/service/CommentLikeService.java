package com.example.testtoy.service;

import com.example.testtoy.domain.comment.Comment;
import com.example.testtoy.domain.commentlike.CommentLike;
import com.example.testtoy.domain.member.Member;
import com.example.testtoy.dto.SaveOrDeleteCommentLikeDto;
import com.example.testtoy.repository.CommentLikeRepository;
import com.example.testtoy.repository.CommentRepository;
import com.example.testtoy.repository.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CommentLikeService {

    private final CommentRepository commentRepository;

    private final CommentLikeRepository commentLikeRepository;

    private final MemberRepository memberRepository;


    public CommentLikeService(CommentRepository commentRepository, CommentLikeRepository commentLikeRepository, MemberRepository memberRepository) {
        this.commentRepository = commentRepository;
        this.commentLikeRepository = commentLikeRepository;
        this.memberRepository = memberRepository;
    }

    /**
    *
    * @method : likeOrUnlikeComment
    *
    * @explain : 댓글의 좋아요/좋아요 취소
    * @author : User
    * @date : 2023-05-09
    *
    **/
    @Transactional
    public ResponseEntity likeOrUnlikeComment(SaveOrDeleteCommentLikeDto saveOrDeleteCommentLikeDto){

        Long commentId = saveOrDeleteCommentLikeDto.getCommentid();
        Long memberId = saveOrDeleteCommentLikeDto.getMemberid();

        // 댓글 존재 여부 확인
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(IllegalArgumentException::new);

        // 댓글 좋아요 존재 여부 확인
        CommentLike commentLike = commentLikeRepository.findCommentLikeByComment_IdAndMember_Id(commentId,memberId);

        // 댓글 좋아요가 존재한다면,
        if(commentLike!=null){
            // 댓글 좋아요 카운트 -1
            comment.updateCommentLikes(comment.getLikes()-1);
            commentLikeRepository.deleteCommentLikeByComment_IdAndMember_Id(commentId,memberId);

            return ResponseEntity.ok(204);
        }
        // 댓글 좋아요가 존재하지 않는다면,
        else{
            Member member = memberRepository.findOne(saveOrDeleteCommentLikeDto.getMemberid());
            // 댓글 좋아요 카운트 +1
            comment.updateCommentLikes(comment.getLikes()+1);
            // 댓글 좋아요
            CommentLike saveCommentLike = CommentLike.createCommentLike(comment,member);

            commentLikeRepository.save(saveCommentLike);
            return ResponseEntity.ok(201);
        }
    }
}
