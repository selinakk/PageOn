package com.tmtb.pageon.test.controller;

import com.tmtb.pageon.comment.model.CommentVO;
import com.tmtb.pageon.comment.controller.CommentController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class TestController {

    @Autowired
    private CommentController controller;

    @GetMapping("/test")
    public String testPage(Model model,
                           @RequestParam(defaultValue = "1") int cpage,
                           @RequestParam(defaultValue = "20") int pageBlock) {
        log.info("testPage()... cpage: {}, pageBlock: {}", cpage, pageBlock);

        // CommentController의 selectAll() 메서드를 호출하여 댓글 데이터를 가져옵니다.
        Map<String, Object> commentsData = controller.selectAll("forum", null, 1, null, cpage, pageBlock); // type과 부모글의 num(fnum, rnum, bnum)에 맞게 매개변수 추가하시면됩니다.

        // 댓글 목록 가져오기
        List<CommentVO> comments = (List<CommentVO>) commentsData.get("comments");

        // 전체 댓글 수 가져오기
        int totalRows = (int) commentsData.get("totalRows");

        // 총 페이지 수 계산 후 모델에 추가
        model.addAttribute("totalPageCount", (int) Math.ceil((double) totalRows / pageBlock));

        // 댓글 목록을 모델에 추가
        model.addAttribute("comments", comments);

        // cpage와 pageBlock을 모델에 추가
        model.addAttribute("cpage", cpage);
        model.addAttribute("pageBlock", pageBlock);

        return "comment/test"; // 타임리프 페이지 경로
    }

}
