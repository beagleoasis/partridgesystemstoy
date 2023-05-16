package com.example.testtoy.domain.boardlike.controller;

import com.example.testtoy.domain.boardlike.domain.SaveOrDeleteBoardLikeDto;
import com.example.testtoy.domain.boardlike.service.BoardLikeFacadeService;
import com.example.testtoy.domain.boardlike.service.BoardLikeService;
import com.example.testtoy.domain.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("boards/boardLike")
@RestController
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
    @Operation(summary = "게시글 좋아요", description = "게시글을 좋아요합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 좋아요/취소가 완료되었습니다."),
            @ApiResponse(responseCode = "500", description = "게시글 좋아요/취소가 실패하였습니다.")
    })
    @PostMapping("")
    public ResponseEntity<Void> likeBoard(@RequestBody SaveOrDeleteBoardLikeDto saveOrDeleteBoardLikeDto){

        boardLikeFacadeService.likeOrUnlikeBoard(saveOrDeleteBoardLikeDto);

        return ResponseEntity.ok().build();
    }


}
