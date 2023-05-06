package com.example.testtoy.ctrl;

import com.example.testtoy.domain.board.Board;
import com.example.testtoy.domain.member.Member;
import com.example.testtoy.dto.SavePostDto;
import com.example.testtoy.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequestMapping("boards")
@Controller
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    /**
    *
    * @method : getBoards
    *
    * @explain : 게시판 조회
    * @author : User
    * @date : 2023-05-04
    *
    **/
    @GetMapping("")
    public String getBoards(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            Model model){

        Page<Board> boards = boardService.findAllBoards(pageable);

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

        HttpSession session = request.getSession();

        if(session.getAttribute("user")==null){
            return "members/login";
        }
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

        redirectView.setUrl("/boards");

        boardService.save(board);

        mav.setView(redirectView);

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
    @DeleteMapping("deletion/{id}")
    public ResponseEntity deleteBoard(@PathVariable Long id){

        Long result = boardService.deleteBoard(id);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public String getBoard(@PathVariable Long id, Model model){

        Board board = boardService.findBoard(id);

        model.addAttribute("board", board);

        return "board/board_detail";
    }

}
