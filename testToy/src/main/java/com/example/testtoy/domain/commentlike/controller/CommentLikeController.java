package com.example.testtoy.domain.commentlike.controller;

import com.example.testtoy.domain.commentlike.domain.SaveOrDeleteCommentLikeDto;
import com.example.testtoy.domain.commentlike.service.CommentLikeFacadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("comments/commentLike")
@Controller
@RequiredArgsConstructor
public class CommentLikeController {
    private final CommentLikeFacadeService commentLikeFacadeService;

    /**
    *
    * @method : likeComment
    *
    * @explain : 댓글 좋아요 처리
    * @author : User
    * @date : 2023-05-09
    *
    **/
    @Operation(summary = "댓글 좋아요/취소", description = "게시글의 댓글에 대해 좋아요/취소를 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "댓글 좋아요/취소가 완료되었습니다."),
            @ApiResponse(responseCode = "500", description = "댓글 좋아요/취소가 실패하였습니다.")
    })
    @PostMapping("")
    public ResponseEntity<Void> likeComment(@RequestBody SaveOrDeleteCommentLikeDto saveOrDeleteCommentLikeDto){

        commentLikeFacadeService.likeOrUnlikeComment(saveOrDeleteCommentLikeDto);

        return ResponseEntity.noContent().build();
    }
}
