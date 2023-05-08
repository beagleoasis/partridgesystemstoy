package com.example.testtoy.ctrl;

import com.example.testtoy.domain.board.Board;
import com.example.testtoy.domain.boardlike.BoardLike;
import com.example.testtoy.domain.member.Member;
import com.example.testtoy.dto.SaveOrDeleteBoardLikeDto;
import com.example.testtoy.service.BoardLikeService;
import com.example.testtoy.service.BoardService;
import com.example.testtoy.service.MemberService;
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

    //private final MemberService memberService;

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

        return boardLikeService.likeOrUnlikeBoard(saveOrDeleteBoardLikeDto);
    }


}
