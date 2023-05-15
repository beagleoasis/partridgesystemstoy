package com.example.testtoy.domain.boardlike.service;

import com.example.testtoy.domain.board.domain.Board;
import com.example.testtoy.domain.board.service.BoardService;
import com.example.testtoy.domain.boardlike.domain.BoardLike;
import com.example.testtoy.domain.boardlike.domain.SaveOrDeleteBoardLikeDto;
import com.example.testtoy.domain.member.domain.Member;
import com.example.testtoy.domain.member.service.MemberService;
import com.example.testtoy.global.CustomException;
import com.example.testtoy.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardLikeFacadeService {

    private final BoardService boardService;

    private final MemberService memberService;

    private final  BoardLikeService boardLikeService;

    @Transactional
    public ResponseEntity likeOrUnlikeBoard(SaveOrDeleteBoardLikeDto saveOrDeleteBoardLikeDto){

        Long boardId = saveOrDeleteBoardLikeDto.getBoardid();
        Long memberId = saveOrDeleteBoardLikeDto.getMemberid();

        // 게시글 좋아요 존재 여부 확인
        Optional<Board> board = boardService.getBoard(boardId);

        Optional<BoardLike> boardLike = boardLikeService.getBoardLike(boardId,memberId);

        // 게시글 좋아요가 존재한다면,
        if(boardLike.isPresent()){
            // 게시글 좋아요 카운트 -1
            board.get().decreaseBoardLikes();

            // 좋아요 취소
            boardLikeService.deleteBoardLike(boardId,memberId);
            return ResponseEntity.noContent().build();
        }
        // 게시글 좋아요가 존재하지 않는다면,
        else{
            Member member = memberService.findOneById(memberId)
                    .orElseThrow(()->new CustomException(ErrorCode.ID_NOT_FOUND));

            // 게시글 좋아요 카운트 +1
            board.get().increaseBoardLikes();
            // 좋아요
            BoardLike saveBoardLike = BoardLike.createBoardLike(board.get(),member);

            boardLikeService.save(saveBoardLike);

            return ResponseEntity.status(201).build();
        }

    }

}
