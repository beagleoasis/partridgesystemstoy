package com.example.testtoy.domain.boardlike.repository;

import com.example.testtoy.domain.boardlike.domain.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {


    // 게시글 좋아요 조회
    Optional <BoardLike> findBoardLikeByBoard_IdAndMember_Id(Long boardId, Long memberId);

    // 게시글 삭제 처리
    void deleteBoardLikeByBoard_IdAndAndMember_Id(Long boardId, Long memberId);

}
