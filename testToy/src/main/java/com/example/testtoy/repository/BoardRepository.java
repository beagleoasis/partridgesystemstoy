package com.example.testtoy.repository;

import com.example.testtoy.domain.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // 최신순
    Page<Board> findBoardsByStateIsNullOrderByIdDesc(Pageable pageable);

    // 방문자순
    Page<Board> findBoardsByStateIsNullOrderByVisitDesc(Pageable pageable);

    // 게시글 추천 순
    Page<Board> findBoardsByStateIsNullOrderByLikesDesc(Pageable pageable);

    // 댓글 추천 순

    // 방문자수+게시글 추천수 순

    // 게시글 추천수+댓글 추천수 순


}
