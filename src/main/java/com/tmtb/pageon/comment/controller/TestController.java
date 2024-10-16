package com.tmtb.pageon.comment.controller;

import com.tmtb.pageon.comment.model.CommentVO;
import com.tmtb.pageon.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TestController {

    @Autowired
    private CommentService service;

    @GetMapping("/test")
    public String testPage(Model model) {
        List<CommentVO> comments = service.selectAll("forum", null, 1, null); // fnum이 1인 댓글 가져오기
        model.addAttribute("comments", comments);
        return "comment/test"; // JSP 페이지 경로
    }
}
