package com.example.testtoy.domain.commentlike.service;

import com.example.testtoy.domain.commentlike.domain.CommentLike;
import com.example.testtoy.domain.commentlike.repository.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    /**
    *
    * @method : findCommentLikeByComment_IdAndMember_Id
    *
    * @explain : 댓글 좋아요 조회
    * @author : User
    * @date : 2023-05-11
    *
    **/
    @Transactional
    public Optional<CommentLike> findCommentLikeByComment_IdAndMember_Id(Long commentId, Long memberId){
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
