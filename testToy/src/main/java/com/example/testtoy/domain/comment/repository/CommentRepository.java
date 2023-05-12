package com.example.testtoy.domain.comment.repository;

import com.example.testtoy.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 삭제되지 않은 댓글 목록 조회
    List<Comment> findByBoardIdAndStateIsNull(Long id);
}
