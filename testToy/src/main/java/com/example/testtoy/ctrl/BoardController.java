package com.example.testtoy.ctrl;

import com.example.testtoy.domain.board.Board;
import com.example.testtoy.domain.member.Member;
import com.example.testtoy.dto.SavePostDto;
import com.example.testtoy.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("")
    public String getBoards(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            Model model){

        Page<Board> boards = boardService.findAllBoards(pageable);

        model.addAttribute("boards", boards);

        return "board/board";
    }


    @GetMapping("post")
    public String getPost(HttpServletRequest request){

        HttpSession session = request.getSession();

        if(session.getAttribute("user")==null){
            return "members/login";
        }
        return "board/board_post";
    }

    @PostMapping("post")
    public ModelAndView writePost(Board board){

        ModelAndView mav = new ModelAndView();

        RedirectView redirectView = new RedirectView();

        redirectView.setUrl("/boards");

        boardService.save(board);

        mav.setView(redirectView);

        return mav;
    }

}
