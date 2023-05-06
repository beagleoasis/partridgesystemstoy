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

@RequestMapping("comments")
@Controller
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("")
    @ResponseBody
    public Comment saveComment(@RequestBody SaveCommentDto saveCommentDto){

        Comment comment = new Comment();
        Board board = new Board();
        board.setId(saveCommentDto.getBoard_id());
        comment.setBoard(board);
        comment.setContent(saveCommentDto.getContent());
        comment.setMemberid(saveCommentDto.getMemberid());

        System.out.println(saveCommentDto.getMemberid());
        System.out.println(saveCommentDto.getContent());
        Comment savedComment = commentService.save(comment);

        return savedComment;
    }
}
