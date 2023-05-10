package com.example.testtoy.service;

import com.example.testtoy.domain.board.Board;
import com.example.testtoy.domain.comment.Comment;
import com.example.testtoy.dto.SaveCommentDto;
import com.example.testtoy.repository.CommentRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
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
    public List<Comment> getComments(Long id){
        return commentRepository.findByBoardIdAndStateIsNull(id);
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
                .orElseThrow(IllegalArgumentException::new);

        comment.updateCommentState("d");

        return id;
    }
}
