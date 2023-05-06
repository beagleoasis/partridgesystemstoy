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

    @Transactional
    public Page<Board> findAllBoards(Pageable pageable){

        // 삭제된 게시글을 제외한 모든 게시글 조회
        return boardRepository.findBoardsByStateIsNull(pageable);
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
}
