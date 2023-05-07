package com.example.testtoy.ctrl;

import com.example.testtoy.domain.board.Board;
import com.example.testtoy.domain.comment.Comment;
import com.example.testtoy.dto.SaveCommentDto;
import com.example.testtoy.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("comments")
@Controller
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("")
    @ResponseBody
    public List<Comment> saveComment(@RequestBody SaveCommentDto saveCommentDto){

        // 이 부분에서 Comment Entity의 board_id 부분을 어떻게 처리할지
        // 비효율 원인 : Comment Entity 내의 board_id를 Board 객체로 받기 때문
        Comment comment = new Comment();
        Board board = new Board();
        board.setId(saveCommentDto.getBoard_id());
        comment.setBoard(board);
        comment.setName(saveCommentDto.getName());
        comment.setContent(saveCommentDto.getContent());
        comment.setMemberid(saveCommentDto.getMemberid());

        Comment savedComment = commentService.save(comment);

        return commentService.getComments(saveCommentDto.getBoard_id());
    }
}
