package com.example.testtoy.domain.board.service;

import com.example.testtoy.domain.board.domain.Board;
import com.example.testtoy.domain.board.repository.BoardRepository;
import com.example.testtoy.global.CustomException;
import com.example.testtoy.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    /**
     *
     * @method : save
     *
     * @explain : 게시글 저장
     * @author : User
     * @date : 2023-05-09
     *
     **/
    @Transactional
    public Board save(Board board){
        return boardRepository.save(board);
    }

    /**
    *
    * @method : findByIdAndStateIsNull
    *
    * @explain : 게시글 조회
    * @author : User
    * @date : 2023-05-11
    *
    **/
    public Board findByIdAndStateIsNull(Long boardId){

        return boardRepository.findByIdAndStateIsNull(boardId)
                .orElseThrow(()->new CustomException(ErrorCode.BOARD_ID_NOT_FOUND));
    }

    /**
    *
    * @method : findAllBoards
    *
    * @explain : 게시판 정렬 조회
    * @author : User
    * @date : 2023-05-08
    *
    **/
    @Transactional
    public Page<Board> findAllBoardsBySortType(Pageable pageable, String sortType){

        // 방문자수 순
        switch (sortType){
            case "visiter":
                return boardRepository.findBoardsByStateIsNullOrderByVisitDesc(pageable);
            case "likeBoard":
                return boardRepository.findBoardsByStateIsNullOrderByLikesDesc(pageable);
            case "visiterAndLikeBoard":
                return boardRepository.findBoardsByStateIsNullOrderByVisitAndBoardLikeDesc(pageable);
            case "likeComment":
                return boardRepository.findBoardsByStateIsNullOrderByCommentsLikes(pageable);
            case "likeBoardAndLikeComment":
                return boardRepository.findBoardsByStateIsNullOrderByBoardLikeANDCommentsLikesDesc(pageable);
            default:
                return boardRepository.findBoardsByStateIsNullOrderByIdDesc(pageable);
        }

    }

    /**
    *
    * @method : deleteBoard
    *
    * @explain : 게시물 삭제 처리(상태값을 'd'로 변경)
    * @author : User
    * @date : 2023-05-06
    *
    **/
    @Transactional
    public void deleteBoard(Long id){
        Board board = boardRepository.findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.Member_ID_NOT_FOUND));

        // 상태값을 d로 변경
        board.updateBoardState("d");
    }

    /**
    *
    * @method : findByIdAndIncreaseVisit
    *
    * @explain : 게시글 상세 페이지 조회 및 조회수 +1 처리
    * @author : User
    * @date : 2023-05-08
    *
    **/
    @Transactional
    public Board findByIdAndIncreaseVisit(Long id){
        Board board = boardRepository.findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.ID_NOT_FOUND));

        // 게시글 조회수 증가 처리
        board.increaseBoardVisit();

        return board;
    }

    /**
    *
    * @method : findById
    *
    * @explain : 게시글 id로 조회
    * @author : User
    * @date : 2023-05-09
    *
    **/
    public Board findById(Long boardId){
        return boardRepository.findById(boardId)
                .orElseThrow(()->new CustomException(ErrorCode.ID_NOT_FOUND));
    }
}
