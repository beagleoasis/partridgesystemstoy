package com.example.testtoy.service;

import com.example.testtoy.domain.board.Board;
import com.example.testtoy.domain.boardlike.BoardLike;
import com.example.testtoy.domain.member.Member;
import com.example.testtoy.dto.SaveOrDeleteBoardLikeDto;
import com.example.testtoy.repository.BoardLikeRepository;
import com.example.testtoy.repository.BoardRepository;
import com.example.testtoy.repository.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BoardLikeService {

    private final BoardLikeRepository boardLikeRepository;

    private final BoardRepository boardRepository;

    private final MemberRepository memberRepository;


    public BoardLikeService(BoardLikeRepository boardLikeRepository, BoardRepository boardRepository, MemberRepository memberRepository) {
        this.boardLikeRepository = boardLikeRepository;
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
    }


    /**
    *
    * @method : getBoardLikeByBoardAndUser
    *
    * @explain : 게시글 좋아요 여부 조회
    * @author : User
    * @date : 2023-05-08
    *
    **/
    @Transactional
    public String getBoardLikeByBoardAndUser(Long boardId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Member member = (Member) authentication.getPrincipal();

        // 게시글을 현재 로그인 유저가 추천했는지 확인하는 변수
        String boardLikeFlag = "F";

        if(member==null){
            return boardLikeFlag;
        }

        Long memberId = member.getId(); // User 엔티티에 getId() 메서드가 있다고 가정

        // 게시글 좋아요 존재 여부 확인
        BoardLike boardLike = boardLikeRepository.findBoardLikeByBoard_IdAndMember_Id(boardId,memberId);


        if(boardLike!=null){
            boardLikeFlag = "T";
        }

        return boardLikeFlag;

    }

    /**
    *
    * @method : likeOrUnlikeBoard
    *
    * @explain : 게시글 추천/추천 취소
    * @author : User
    * @date : 2023-05-08
    *
    **/
    @Transactional
    public ResponseEntity likeOrUnlikeBoard(SaveOrDeleteBoardLikeDto saveOrDeleteBoardLikeDto) {

        Long boardId = saveOrDeleteBoardLikeDto.getBoardid();
        Long memberId = saveOrDeleteBoardLikeDto.getMemberid();

        // 게시글 존재 여부 확인
        Board board = boardRepository.findById(boardId)
                .orElseThrow(IllegalArgumentException::new);

        // 게시글 좋아요 존재 여부 확인
        BoardLike boardLike = boardLikeRepository.findBoardLikeByBoard_IdAndMember_Id(boardId,memberId);

        // 게시글 좋아요가 존재한다면,
        if(boardLike!=null){
            // 게시글 좋아요 카운트 -1
            //board.updateBoardLikes(board.getLikes()-1);
            board.decreaseBoardLikes();
            // 좋아요 취소
            boardLikeRepository.deleteBoardLikeByBoard_IdAndAndMember_Id(boardId,memberId);
            return ResponseEntity.ok(204);
        }
        // 게시글 좋아요가 존재하지 않는다면,
        else{
            Member member = memberRepository.findOneById(saveOrDeleteBoardLikeDto.getMemberid());
            // 게시글 좋아요 카운트 +1
            //board.updateBoardLikes(board.getLikes()+1);
            board.increaseBoardLikes();
            // 좋아요
            BoardLike saveBoardLike = BoardLike.createBoardLike(board,member);

            boardLikeRepository.save(saveBoardLike);
            return ResponseEntity.ok(201);
        }
    }
}
