package com.example.testtoy.service;

import com.example.testtoy.domain.board.Board;
import com.example.testtoy.domain.boardlike.BoardLike;
import com.example.testtoy.repository.BoardLikeRepository;
import com.example.testtoy.repository.BoardRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BoardLikeService {

    private final BoardLikeRepository boardLikeRepository;

    private final BoardRepository boardRepository;

    public BoardLikeService(BoardLikeRepository boardLikeRepository, BoardRepository boardRepository) {
        this.boardLikeRepository = boardLikeRepository;
        this.boardRepository = boardRepository;
    }


    /**
    *
    * @method : getBoardLikeByBoardAndUser
    *
    * @explain : 게시글 좋아요 조회
    * @author : User
    * @date : 2023-05-08
    *
    **/
    @Transactional
    public BoardLike getBoardLikeByBoardAndUser(Long boardId, Long memberId){

        return boardLikeRepository.findBoardLikeByBoard_IdAndMember_Id(boardId,memberId);
    }

    /**
    *
    * @method : save
    *
    * @explain : 게시글 좋아요 생성
    * @author : User
    * @date : 2023-05-08
    *
    **/
    @Transactional
    public BoardLike save(BoardLike boardLike){
        // 게시글 좋아요 수 +1 처리를 위한 로직
        Board board = boardRepository.findById(boardLike.getBoard().getId())
                .orElseThrow(IllegalArgumentException::new);

        board.setLikes(board.getLikes()+1);

        // 게시글 좋아요 생성
        return boardLikeRepository.save(boardLike);
    }


    @Transactional
    public void delete(Long boardId, Long memberId){

        // 게시글 좋아요 수 -1 처리를 위한 로직
        Board board = boardRepository.findById(boardId)
                .orElseThrow(IllegalArgumentException::new);

        board.setLikes(board.getLikes()-1);

        // 게시글 좋아요 삭제
        boardLikeRepository.deleteBoardLikeByBoard_IdAndAndMember_Id(boardId, memberId);
    }

}
