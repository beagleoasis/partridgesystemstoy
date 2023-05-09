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

    /**
    *
    * @method : likeComment
    *
    * @explain : 게시글 좋아요 처리
    * @author : User
    * @date : 2023-05-09
    *
    **/
    @PostMapping("")
    @ResponseBody
    public ResponseEntity likeComment(@RequestBody SaveOrDeleteCommentLikeDto saveOrDeleteCommentLikeDto){

        return commentLikeService.likeOrUnlikeComment(saveOrDeleteCommentLikeDto);
    }
}
