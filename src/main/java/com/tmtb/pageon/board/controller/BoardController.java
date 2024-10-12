package com.tmtb.pageon.board.controller;

import com.tmtb.pageon.board.service.BoardService;
import com.tmtb.pageon.mapper.BoardMapper;
import com.tmtb.pageon.board.model.BoardVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board")
    public String getboard(Model model) {
        log.info("게시판 메인 페이지");
        List<BoardVO> board = boardService.getAllBoard();
        model.addAttribute("board", board);
        return "board";
    }




}
