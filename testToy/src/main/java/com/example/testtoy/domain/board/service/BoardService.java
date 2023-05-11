package com.example.testtoy.domain.board.service;

import com.example.testtoy.domain.board.domain.Board;
import com.example.testtoy.domain.board.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /**
    *
    * @method : getBoard
    *
    * @explain : 게시글 조회
    * @author : User
    * @date : 2023-05-11
    *
    **/
    public Optional<Board> getBoard(Long boardId){

        return boardRepository.findByIdAndStateIsNull(boardId);
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
        if(sortType.equals("visiter")){
            return boardRepository.findBoardsByStateIsNullOrderByVisitDesc(pageable);
        }
        // 게시글 추천수 순
        else if(sortType.equals("likeBoard")){
            return boardRepository.findBoardsByStateIsNullOrderByLikesDesc(pageable);
        }
        // 방문자수 + 게시글 추천수 순
        else if(sortType.equals("visiterAndLikeBoard")){
            return boardRepository.findBoardsByStateIsNullOrderByVisitAndBoardLikeDesc(pageable);
        }
        // 댓글 추천수 순
        else if(sortType.equals("likeComment")){
            return boardRepository.findBoardsByStateIsNullOrderByCommentsLikes(pageable);
        }
        // 게시글 추천수 + 댓글 추천수 순
        else if(sortType.equals("likeBoardAndLikeComment")){
            return boardRepository.findBoardsByStateIsNullOrderByBoardLikeANDCommentsLikesDesc(pageable);
        }
        // 최신순 디폴트
        else{
            return boardRepository.findBoardsByStateIsNullOrderByIdDesc(pageable);
        }

    }

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
    * @method : deleteBoard
    *
    * @explain : 게시물 삭제 처리(상태값을 'd'로 변경)
    * @author : User
    * @date : 2023-05-06
    *
    **/
    @Transactional
    public Long deleteBoard(Long id){
        Board board = boardRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        // 상태값을 d로 변경
        board.updateBoardState("d");

        return id;
    }

    /**
    *
    * @method : findBoard
    *
    * @explain : 게시글 상세 페이지 조회 및 조회수 +1 처리
    * @author : User
    * @date : 2023-05-08
    *
    **/
    @Transactional
    public Board findBoard(Long id){
        Board board = boardRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        if(board!=null){
            // 게시글 조회수 증가 처리
            // 동시성 문제 및 조회수 증가 관련 로직 추가 필요
            //board.updateBoardVisit(board.getVisit()+1);
            board.increaseBoardVisit();
        }

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
                .orElseThrow(()-> new RuntimeException());
    }
}
