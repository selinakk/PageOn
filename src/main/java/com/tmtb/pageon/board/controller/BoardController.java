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

    @GetMapping("/freeboard")
    public String freeboard(Model model) {
        log.info("자유게시판 페이지");

        List<BoardVO> boardList = boardService.findAll();

        model.addAttribute("boardList", boardList);
        log.info("boardList : " + boardList);
        return "freeboard";
    }


}
