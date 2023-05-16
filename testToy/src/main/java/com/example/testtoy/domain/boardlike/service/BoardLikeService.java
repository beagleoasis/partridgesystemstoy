package com.example.testtoy.domain.boardlike.service;

import com.example.testtoy.domain.boardlike.domain.BoardLike;
import com.example.testtoy.domain.boardlike.repository.BoardLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardLikeService {

    private final BoardLikeRepository boardLikeRepository;

    /**
    *
    * @method : findBoardLikeByBoard_IdAndMember_Id
    *
    * @explain : 게시글 좋아요 조회
    * @author : User
    * @date : 2023-05-11
    *
    **/
    public Optional<BoardLike> findBoardLikeByBoard_IdAndMember_Id(Long boardId, Long memberId){
        return boardLikeRepository.findBoardLikeByBoard_IdAndMember_Id(boardId,memberId);
    }

    /**
    *
    * @method : deleteBoardLike
    *
    * @explain : 게시글 좋아요 삭제
    * @author : User
    * @date : 2023-05-11
    *
    **/
    @Transactional
    public  void deleteBoardLike(Long boardId, Long memberId){
        boardLikeRepository.deleteBoardLikeByBoard_IdAndAndMember_Id(boardId,memberId);
    }

    /**
    *
    * @method : save
    *
    * @explain : 게시글 좋아요 저장
    * @author : User
    * @date : 2023-05-11
    *
    **/
    @Transactional
    public BoardLike save(BoardLike boardLike){
        return boardLikeRepository.save(boardLike);
    }

}
