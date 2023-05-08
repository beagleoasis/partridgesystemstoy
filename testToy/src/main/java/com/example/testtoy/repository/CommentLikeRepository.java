package com.example.testtoy.repository;

import com.example.testtoy.domain.commentlike.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    // 댓글 좋아요 조회
    CommentLike findCommentLikeByComment_IdAndMember_Id(Long commentId, Long memberId);

    // 게시글 삭제 처리
    void deleteCommentLikeByComment_IdAndMember_Id(Long commentId, Long memberId);

}