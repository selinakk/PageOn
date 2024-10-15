package com.tmtb.pageon.forum.controller;

import com.tmtb.pageon.forum.model.ForumVO;
import com.tmtb.pageon.forum.service.ForumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
public class ForumController {

    @Autowired
    ForumService service;

    @GetMapping("/forum/list")
    public String forumList(Model model,
                            @RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "4") int size,
                            @RequestParam(defaultValue = "num") String sortField,
                            @RequestParam(defaultValue = "asc") String sortDir
                            ) {
        List<ForumVO> list = service.getList(page, size, sortField, sortDir);
        int totalList = service.getListCount();
        int totalPages = (int)Math.ceil((double)totalList/size);

        model.addAttribute("list", list);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalList", totalList);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        log.info("/forum/list - 토론 목록 페이지 ");

        return "forum/list";
    }
}
