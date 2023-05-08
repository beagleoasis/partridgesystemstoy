package com.example.testtoy.service;

import com.example.testtoy.domain.board.Board;
import com.example.testtoy.dto.SavePostDto;
import com.example.testtoy.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /**
    *
    * @method : findAllBoards
    *
    * @explain : 게시판 최신순 조회
    * @author : User
    * @date : 2023-05-08
    *
    **/
    @Transactional
    public Page<Board> findAllBoardsBySortType(Pageable pageable, String sortType){

        System.out.println("sortType : " + sortType);

        // 방문자수 순
        if(sortType.equals("visiter")){
            System.out.println("visiter 진입 확인");
            return boardRepository.findBoardsByStateIsNullOrderByVisitDesc(pageable);
        }
        // 게시글 추천수 순
        else if(sortType.equals("likeBoard")){
            return boardRepository.findBoardsByStateIsNullOrderByLikesDesc(pageable);
        }
        // 방문자수 + 게시글 추천수 순
        else if(sortType.equals("visiterAndLikeBoard")){

        }
        // 댓글 추천수 순
        else if(sortType.equals("likeComment")){
            return boardRepository.findBoardsByStateIsNullOrderByCommentsLikes(pageable);
        }
        // 게시글 추천수 + 댓글 추천수 순
        else if(sortType.equals("likeBoardAndLikeComment")){

        }
        // 최신순 디폴트
        else{
            System.out.println("else 진입 확인");
            return boardRepository.findBoardsByStateIsNullOrderByIdDesc(pageable);
        }

        // 삭제된 게시글을 제외한 모든 게시글 조회
        return boardRepository.findBoardsByStateIsNullOrderByIdDesc(pageable);
    }

    @Transactional
    public Board save(Board board){
        return boardRepository.save(board);
    }

    /**
    *
    * @method : deleteBoard
    *
    * @explain : 게시물 삭제 처리(상태값을 'd'로 변경
    * @author : User
    * @date : 2023-05-06
    *
    **/
    @Transactional
    public Long deleteBoard(Long id){
        Board board = boardRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        // 상태값을 d로 변경
        board.setState("d");

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
            board.setVisit(board.getVisit()+1);
            boardRepository.save(board);
        }

        return board;
    }

    public Board findById(Long boardId){
        return boardRepository.findById(boardId)
                .orElseThrow(()-> new RuntimeException());
    }
}
