package com.example.testtoy.domain.boardlike.controller;

import com.example.testtoy.domain.boardlike.domain.SaveOrDeleteBoardLikeDto;
import com.example.testtoy.domain.boardlike.service.BoardLikeFacadeService;
import com.example.testtoy.domain.boardlike.service.BoardLikeService;
import com.example.testtoy.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("boards/boardLike")
@Controller
@RequiredArgsConstructor
public class BoardLikeController {
    private final BoardLikeFacadeService boardLikeFacadeService;

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

        return boardLikeFacadeService.likeOrUnlikeBoard(saveOrDeleteBoardLikeDto);
    }


}
