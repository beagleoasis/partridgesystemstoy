package com.example.testtoy.domain.comment.service;

import com.example.testtoy.domain.board.domain.Board;
import com.example.testtoy.domain.comment.domain.Comment;
import com.example.testtoy.domain.comment.domain.SaveCommentDto;
import com.example.testtoy.domain.comment.repository.CommentRepository;
import com.example.testtoy.global.CustomException;
import com.example.testtoy.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    /**
    *
    * @method : getCommentById
    *
    * @explain : 댓글 조회
    * @author : User
    * @date : 2023-05-11
    *
    **/
    @Transactional
    public Comment getCommentById(Long commentId){

        return commentRepository.findById(commentId)
                .orElseThrow(()->new CustomException(ErrorCode.ID_NOT_FOUND));
        // 추후 CustomException으로 예외 처리
    }

    /**
    *
    * @method : getComments
    *
    * @explain : 댓글 리스트 조회
    * @author : User
    * @date : 2023-05-06
    *
    **/
    @Transactional
    public List<Comment> getComments(Long commentId){
        return commentRepository.findByBoardIdAndStateIsNull(commentId);
    }

    /**
    *
    * @method : save
    *
    * @explain : 댓글 저장
    * @author : User
    * @date : 2023-05-06
    *
    **/
    @Transactional
    public Comment save(SaveCommentDto saveCommentDto){

        Board board = Board.createBoardForComment(saveCommentDto.getBoard_id());

        Comment comment = Comment.createComment(board, saveCommentDto.getName(), saveCommentDto.getContent(), saveCommentDto.getMemberid());


        return commentRepository.save(comment);
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
    @Transactional
    public Long deleteComment(Long id){
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()->new CustomException(ErrorCode.ID_NOT_FOUND));

        comment.updateCommentState("d");

        return id;
    }
}
