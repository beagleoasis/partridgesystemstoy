package com.example.testtoy.repository;

import com.example.testtoy.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBoardIdAndStateIsNull(Long id); // 삭제되지 않은 댓글 목록 조회
}