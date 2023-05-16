package com.example.testtoy.domain.board.controller;

import com.example.testtoy.domain.board.domain.Board;
import com.example.testtoy.domain.comment.domain.Comment;
import com.example.testtoy.domain.board.service.BoardService;
import com.example.testtoy.domain.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    /**
    *
    * @method : getBoards
    *
    * @explain : 게시판 최신순 조회
    * @author : User
    * @date : 2023-05-04
    *
    **/
    @Operation(summary = "게시판 조회", description = "게시판을 sortType에 따라 정렬해 조회합니다.")
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
    @Operation(summary = "게시글 작성 페이지", description = "글쓰기 버튼을 눌러 게시글 작성 페이지로 이동합니다.")
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
    @Operation(summary = "게시글 작성", description = "게시글 작성페이지에서 게시글을 작성합니다.")
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
    @Operation(summary = "게시글 삭제", description = "해당 게시글을 삭제(상태값 d로 변경)합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "게시글 삭제가 완료되었습니다."),
            @ApiResponse(responseCode = "500", description = "게시글 삭제가 실패하였습니다.")
    })
    @PutMapping("deletion/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id){

        boardService.deleteBoard(id);

        return ResponseEntity.noContent().build();
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
    @Operation(summary = "게시글 상세 페이지 조회", description = "특정 게시글을 클릭해 상세 페이지를 조회합니다.")
    @GetMapping("/{boardId}")
    public String getBoard(@PathVariable Long boardId, Model model){

        // 게시글 조회
        Board board = boardService.findByIdAndIncreaseVisit(boardId);

        // 댓글 리스트
        List<Comment> comments = commentService.findByBoardIdAndStateIsNull(board.getId());

        model.addAttribute("board", board);
        model.addAttribute("comments", comments);

        return "board/board_detail";
    }

}
