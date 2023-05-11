package com.example.testtoy.domain.board.repository;

import com.example.testtoy.domain.board.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findByIdAndStateIsNull(Long boardId);

    // 최신순
    Page<Board> findBoardsByStateIsNullOrderByIdDesc(Pageable pageable);

    // 방문자순
    Page<Board> findBoardsByStateIsNullOrderByVisitDesc(Pageable pageable);

    // 게시글 추천 순
    Page<Board> findBoardsByStateIsNullOrderByLikesDesc(Pageable pageable);

    // 댓글 추천 순
    @Query("SELECT b" +
            " FROM Board b " +
            " LEFT JOIN Comment c ON b.id = c.board.id " +
            " WHERE b.state IS NULL " +
            " GROUP BY b.id " +
            " ORDER BY SUM(c.likes) DESC ")
    Page<Board> findBoardsByStateIsNullOrderByCommentsLikes(Pageable pageable);

    // 방문자수+게시글 추천수 순
    @Query("SELECT b" +
            " FROM Board b " +
            " WHERE b.state IS NULL " +
            " GROUP BY b.id " +
            " ORDER BY SUM(b.visit + b.likes) DESC ")
    Page<Board> findBoardsByStateIsNullOrderByVisitAndBoardLikeDesc(Pageable pageable);

    // 게시글 추천수+댓글 추천수 순
    @Query("SELECT b " +
            " FROM Board b " +
            " LEFT JOIN Comment c " +
            " ON b.id = c.board.id " +
            " WHERE b.state IS NULL " +
            " GROUP BY b.id " +
            " ORDER BY b.likes + SUM(c.likes) DESC ")
    Page<Board> findBoardsByStateIsNullOrderByBoardLikeANDCommentsLikesDesc(Pageable pageable);

}
