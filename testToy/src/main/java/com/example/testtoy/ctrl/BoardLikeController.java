package com.example.testtoy.ctrl;

import com.example.testtoy.domain.board.Board;
import com.example.testtoy.domain.boardlike.BoardLike;
import com.example.testtoy.domain.member.Member;
import com.example.testtoy.dto.SaveOrDeleteBoardLikeDto;
import com.example.testtoy.service.BoardLikeService;
import com.example.testtoy.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("boards/boardLike")
@Controller
public class BoardLikeController {

    private final BoardService boardService;

    private final BoardLikeService boardLikeService;

    public BoardLikeController(BoardService boardService, BoardLikeService boardLikeService) {
        this.boardService = boardService;
        this.boardLikeService = boardLikeService;
    }

    /**
     *
     * @method : likeBoard
     *
     * @explain : 게시글 추천/비추천
     * @author : User
     * @date : 2023-05-08
     *
     **/
    @PostMapping("")
    @ResponseBody
    public ResponseEntity likeBoard(@RequestBody SaveOrDeleteBoardLikeDto saveOrDeleteBoardLikeDto){

        Long boardId = saveOrDeleteBoardLikeDto.getBoardid();
        Long memberId = saveOrDeleteBoardLikeDto.getMemberid();

        System.out.println(boardId + " , " + memberId);

        // 게시글 존재 여부 확인
        Board board = boardService.findBoard(boardId);

        if(board==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시글을 찾을 수 없습니다.");
        }

        // 게시글 좋아요 존재 여부 확인
        BoardLike checkBoardLike= boardLikeService.getBoardLikeByBoardAndUser(boardId, memberId);

        // 게시글 좋아요가 존재한다면,
        if(checkBoardLike!=null){
            // 좋아요 삭제 처리

            boardLikeService.delete(boardId, memberId);
            return ResponseEntity.ok(204);
        }
        // 게시글 좋아요가 존재하지 않는다면,
        else{
            // 좋아요 생성 처리

            // 좋아요 값 저장을 위한 BoardLike 객체 생성
            BoardLike boardLike = new BoardLike();

            Board boardParam = new Board();
            Member memberParam = new Member();
            boardParam.setId(boardId);
            memberParam.setId(memberId);
            boardLike.setBoard(boardParam);
            boardLike.setMember(memberParam);

            boardLikeService.save(boardLike);
        }

        return ResponseEntity.ok(201);
    }


}
