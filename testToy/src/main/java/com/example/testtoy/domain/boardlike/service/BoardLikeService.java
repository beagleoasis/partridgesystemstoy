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

    public Optional<BoardLike> getBoardLike(Long boardId, Long memberId){
        return boardLikeRepository.findBoardLikeByBoard_IdAndMember_Id(boardId,memberId);
    }

    @Transactional
    public  void deleteBoardLike(Long boardId, Long memberId){
        boardLikeRepository.deleteBoardLikeByBoard_IdAndAndMember_Id(boardId,memberId);
    }

    @Transactional
    public BoardLike save(BoardLike boardLike){
        return boardLikeRepository.save(boardLike);
    }

}
