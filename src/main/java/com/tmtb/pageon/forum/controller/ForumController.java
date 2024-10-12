package com.tmtb.pageon.forum.controller;

import com.tmtb.pageon.forum.model.ForumVO;
import com.tmtb.pageon.forum.service.ForumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
public class ForumController {

    @Autowired
    ForumService service;

    @GetMapping("/forum/list")
    public String getUsers(Model model) {
        List<ForumVO> list = service.selectAll();
        model.addAttribute("list", list);
        log.info("토론 목록");
        return "forum/list";
    }
}
