package com.example.testtoy.ctrl;

import com.example.testtoy.dto.SaveOrDeleteCommentLikeDto;
import com.example.testtoy.service.CommentFacadeService;
import com.example.testtoy.service.CommentLikeService;
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

    private final CommentFacadeService commentFacadeService;

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

        return commentFacadeService.likeOrUnlikeComment(saveOrDeleteCommentLikeDto);
    }
}
