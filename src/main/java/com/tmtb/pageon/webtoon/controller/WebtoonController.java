package com.tmtb.pageon.webtoon.controller;

import com.tmtb.pageon.board.model.BoardVO;
import com.tmtb.pageon.board.service.BoardService;
import com.tmtb.pageon.webtoon.model.WebtoonVO;
import com.tmtb.pageon.webtoon.service.WebtoonService;
import jakarta.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
public class WebtoonController {

    @Autowired
    private WebtoonService webtoonService;

    @Autowired
    ServletContext context;

    @GetMapping("/wt_selectAll")
    public String wt_selectAll(@RequestParam(defaultValue = "1") int page, Model model, String sort) {
        log.info("웹툰 목록 페이지");


        List<WebtoonVO> webtoonList;
        webtoonList = webtoonService.getWebtoonList();
        model.addAttribute("webtoonList", webtoonList);

        return "webtoon/selectAll";
    }

}
