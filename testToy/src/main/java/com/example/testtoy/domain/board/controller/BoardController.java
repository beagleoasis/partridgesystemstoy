package com.example.testtoy.domain.board.controller;

import com.example.testtoy.domain.board.domain.Board;
import com.example.testtoy.domain.comment.domain.Comment;
import com.example.testtoy.domain.boardlike.service.BoardLikeService;
import com.example.testtoy.domain.board.service.BoardService;
import com.example.testtoy.domain.comment.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("boards")
@Controller
public class BoardController {

    private final BoardService boardService;

    private final CommentService commentService;

    private final BoardLikeService boardLikeService;

    public BoardController(BoardService boardService, CommentService commentService, BoardLikeService boardLikeService) {
        this.boardService = boardService;
        this.commentService = commentService;
        this.boardLikeService = boardLikeService;
    }

    /**
    *
    * @method : getBoards
    *
    * @explain : 게시판 최신순 조회
    * @author : User
    * @date : 2023-05-04
    *
    **/
    @GetMapping("")
    public String getBoards(@RequestParam(name = "sortType", required = false, defaultValue = "latest") String sortType, @PageableDefault(page = 0, size = 10) Pageable pageable,
                            Model model){

        Page<Board> boards = boardService.findAllBoardsBySortType(pageable, sortType);

        model.addAttribute("boards", boards);

        return "board/board";
    }




    /**
    *
    * @method : getPost
    *
    * @explain : 게시물 작성 페이지 조회
    * @author : User
    * @date : 2023-05-05
    *
    **/
    @GetMapping("post")
    public String getPost(HttpServletRequest request){
        return "board/board_post";
    }

    /**
    *
    * @method : writePost
    *
    * @explain : 게시물 작성
    * @author : User
    * @date : 2023-05-05
    *
    **/
    @PostMapping("post")
    public ModelAndView writePost(Board board){

        ModelAndView mav = new ModelAndView();

        RedirectView redirectView = new RedirectView();

        // 게시물 작성
        boardService.save(board);

        mav.setView(redirectView);

        redirectView.setUrl("/boards");

        return mav;
    }

    /**
    *
    * @method : deleteBoard
    *
    * @explain : 게시판-게시물 삭제(상태값 수정을 통한 삭제 처리)
    * @author : User
    * @date : 2023-05-06
    *
    **/
    @PutMapping("deletion/{id}")
    public ResponseEntity deleteBoard(@PathVariable Long id){

        boardService.deleteBoard(id);

        return ResponseEntity.ok(201);
    }

    /**
    *
    * @method : getBoard
    *
    * @explain : 게시글 상세 페이지 조회
    * @author : User
    * @date : 2023-05-06
    *
    **/
    @GetMapping("/{boardId}")
    public String getBoard(@PathVariable Long boardId, Model model){

        // 게시글 조회
        Board board = boardService.findBoard(boardId);

        // 댓글 리스트
        List<Comment> comments = commentService.getComments(board.getId());

        model.addAttribute("board", board);
        model.addAttribute("comments", comments);

        return "board/board_detail";
    }

}
