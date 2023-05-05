package com.example.testtoy.ctrl;

import com.example.testtoy.domain.board.Board;
import com.example.testtoy.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("boards")
@Controller
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("")
    public String getBoards(@PageableDefault(page = 0, size = 10, sort = "board_id", direction = Sort.Direction.DESC) Pageable pageable,
                            Model model){

        Page<Board> boards = boardService.findAllBoards(pageable);

        model.addAttribute("boards", boards);

        return "board/board";
    }

}
