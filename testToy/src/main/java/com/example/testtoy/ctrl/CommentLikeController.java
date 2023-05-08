package com.example.testtoy.ctrl;

import com.example.testtoy.dto.SaveOrDeleteCommentLikeDto;
import com.example.testtoy.service.CommentLikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("comments/commentLike")
@Controller
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    public CommentLikeController(CommentLikeService commentLikeService) {
        this.commentLikeService = commentLikeService;
    }

    @PostMapping("")
    @ResponseBody
    public ResponseEntity likeComment(@RequestBody SaveOrDeleteCommentLikeDto saveOrDeleteCommentLikeDto){

        return commentLikeService.likeOrUnlikeComment(saveOrDeleteCommentLikeDto);
    }
}
