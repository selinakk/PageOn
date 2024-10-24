package com.tmtb.pageon.forum.controller;


import com.tmtb.pageon.forum.model.ForumVO;
import com.tmtb.pageon.forum.service.ForumService;
import com.tmtb.pageon.notice.model.NoticeVO;
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

//    @GetMapping("/n_community2")
//    public String communityMain2(Model model) {
//        log.info("/n_community2");
//
//        List<ForumVO> list = service.community2();
//        log.info("list.size():{}", list.size());
//
//        model.addAttribute("list", list);
//
//        return "notice/community_main";
//    }



}