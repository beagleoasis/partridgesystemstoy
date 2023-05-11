package com.example.testtoy.service;

import com.example.testtoy.domain.comment.Comment;
import com.example.testtoy.domain.commentlike.CommentLike;
import com.example.testtoy.domain.member.Member;
import com.example.testtoy.dto.SaveOrDeleteCommentLikeDto;
import com.example.testtoy.repository.CommentLikeRepository;
import com.example.testtoy.repository.CommentRepository;
import com.example.testtoy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    /**
    *
    * @method : getCommentLikeByCommentIdAndMemberId
    *
    * @explain : 댓글 좋아요 조회
    * @author : User
    * @date : 2023-05-11
    *
    **/
    @Transactional
    public CommentLike getCommentLikeByCommentIdAndMemberId(Long commentId, Long memberId){
        return commentLikeRepository.findCommentLikeByComment_IdAndMember_Id(commentId,memberId);
    }

    /**
    *
    * @method : deleteCommentLikeByCommentIdAndMemberId
    *
    * @explain : 댓글 좋아요 삭제
    * @author : User
    * @date : 2023-05-11
    *
    **/
    @Transactional
    public void deleteCommentLikeByCommentIdAndMemberId(Long commentId, Long memberId){
        commentLikeRepository.deleteCommentLikeByComment_IdAndMember_Id(commentId,memberId);
    }

    /**
    *
    * @method : save
    *
    * @explain : 댓글 좋아요 저장
    * @author : User
    * @date : 2023-05-11
    *
    **/
    @Transactional
    public void save(CommentLike commentLike){
        commentLikeRepository.save(commentLike);
    }


}
