package com.example.testtoy.ctrl;

import com.example.testtoy.domain.board.Board;
import com.example.testtoy.domain.comment.Comment;
import com.example.testtoy.dto.SaveCommentDto;
import com.example.testtoy.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("comments")
@Controller
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
    *
    * @method : saveComment
    *
    * @explain : 게시글에 대한 댓글 작성
    * @author : User
    * @date : 2023-05-07
    *
    **/
    @PostMapping("")
    @ResponseBody
    public List<Comment> saveComment(@RequestBody SaveCommentDto saveCommentDto){

        // 이 부분에서 Comment Entity의 board_id 부분을 어떻게 처리할지
        // 비효율 원인 : Comment Entity 내의 board_id를 Board 객체로 받기 때문

        Comment savedComment = commentService.save(saveCommentDto);

        return commentService.getComments(saveCommentDto.getBoard_id());
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
    @DeleteMapping("deletion/{id}")
    public ResponseEntity deleteComment(@PathVariable Long id){

        Long result = commentService.deleteComment(id);

        return ResponseEntity.ok(result);
    }
}
