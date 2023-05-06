package com.example.testtoy.service;

import com.example.testtoy.domain.board.Board;
import com.example.testtoy.domain.comment.Comment;
import com.example.testtoy.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getComments(Long id){
        return commentRepository.findByBoardIdAndStateIsNull(id);
    }
}
