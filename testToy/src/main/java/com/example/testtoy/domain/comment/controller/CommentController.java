package com.example.testtoy.domain.comment.controller;

import com.example.testtoy.domain.comment.domain.Comment;
import com.example.testtoy.domain.comment.domain.SaveCommentDto;
import com.example.testtoy.domain.commentlike.service.CommentLikeFacadeService;
import com.example.testtoy.domain.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("comments")
@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;


    /**
    *
    * @method : saveComment
    *
    * @explain : 게시글에 대한 댓글 작성
    * @author : User
    * @date : 2023-05-07
    *
    **/
    @Operation(summary = "댓글 작성", description = "게시글 상세 페이지에서 해당 게시글에 대한 댓글을 작성합니다.")
    @PostMapping("")
    @ResponseBody
    public List<Comment> saveComment(@RequestBody SaveCommentDto saveCommentDto){

        // 이 부분에서 Comment Entity의 board_id 부분을 어떻게 처리할지
        // 비효율 원인 : Comment Entity 내의 board_id를 Board 객체로 받기 때문

        Comment savedComment = commentService.save(saveCommentDto);

        return commentService.findByBoardIdAndStateIsNull(saveCommentDto.getBoard_id());
    }

    /**
    *
    * @method : deleteComment
    *
    * @explain : 댓글 삭제(상태값 'd'로 변경)
    * @author : User
    * @date : 2023-05-07
    *
    **/
    @Operation(summary = "댓글 삭제", description = "해당 댓글을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "댓글 삭제가 완료되었습니다."),
            @ApiResponse(responseCode = "500", description = "댓글 삭제가 실패하였습니다.")
    })
    @DeleteMapping("deletion/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id){

        commentService.deleteComment(id);

        return ResponseEntity.noContent().build();
    }
}
