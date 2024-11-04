package com.tmtb.pageon.forum.controller;

import com.tmtb.pageon.comment.controller.CommentController;
import com.tmtb.pageon.comment.model.CommentVO;
import com.tmtb.pageon.forum.model.ForumVO;
import com.tmtb.pageon.forum.service.ForumService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class ForumController {

    @Autowired
    ForumService service;

    @Autowired
    private CommentController controller;

//    목록 시작
    @GetMapping("/forum/list")
    public String forumList(Model model,
                            @RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "4") int size,
                            @RequestParam(defaultValue = "wdate") String sortField,
                            @RequestParam(defaultValue = "desc") String sortDir
                            ) {
        List<ForumVO> list = service.getList(page, size, sortField, sortDir);
        int totalList = service.getListCnt();
        int totalPages = (int)Math.ceil((double)totalList/size);


        model.addAttribute("list", list);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalList", totalList);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        log.info("/forum/list - 토론 목록 페이지");

        return "forum/list";
    }
    @GetMapping("/forum/search")
    public String forumSearch(
            @RequestParam String searchKey,
            @RequestParam String searchWord,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "4") int size,
            Model model) {

        List<ForumVO> list = service.searchForum(searchKey, searchWord, page, size);
        int totalSearchList = service.searchForumCnt(searchKey, searchWord);
        int totalSearchPages = (int) Math.ceil((double) totalSearchList / size);
        if (totalSearchPages < 1) {
            totalSearchPages = 0;
        }
        model.addAttribute("list", list);
        model.addAttribute("searchKey", searchKey);
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("totalSearchList", totalSearchList);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalSearchPages", totalSearchPages);

        log.info("/forum/search - 토론 목록 {} '{}'에 대한 검색 실행 page{}",searchKey,searchWord,page);
        log.info("검색 결과 총 {}건",totalSearchList);
        return "forum/list";
    }
//    목록 끝
//    상세보기 시작
    @GetMapping("/forum/view")
    public String forumView(ForumVO vo,
                            Model model,
                            @RequestParam(defaultValue = "1") int cpage,
                            @RequestParam(defaultValue = "20") int pageBlock) {
        //아래 라인 조회수 증가 메서드
        service.increaseForumHit(vo.getNum());

        ForumVO vo2 = service.selectOne(vo);
        log.info("/forum/view - 토론 상세보기 {}", vo2);

//        이하 이수민님 코드
        // CommentController의 selectAll() 메서드를 호출하여 댓글 데이터를 가져옵니다.
        Map<String, Object> commentsData = controller.selectAll("forum", null, vo2.getNum(), null, cpage, pageBlock); // type과 부모글의 num(fnum, rnum, bnum)에 맞게 매개변수 추가하시면됩니다.
        // 댓글 목록 가져오기
        List<CommentVO> comments = (List<CommentVO>) commentsData.get("comments");

        // 전체 댓글 수 가져오기
        int totalRows = (int) commentsData.get("totalRows");

        model.addAttribute("totalPageCount", (int) Math.ceil((double) totalRows / pageBlock));
        model.addAttribute("comments", comments);
        model.addAttribute("cpage", cpage);
        model.addAttribute("pageBlock", pageBlock);
        model.addAttribute("vo2", vo2);
        return "forum/view";
    }
    @GetMapping("/forum/report")
    public String reportForum(@RequestParam int num){
        service.reportForum(num);
        log.info("신고 TRUE로 설정");
        return "redirect:/forum/view?num=" + num;
    }
//    상세보기 끝
//    DML 시작
    @GetMapping("/forum/write")
    public String insertForum(@RequestParam("work_num")int workNum, Model model) {
        log.info("/forum/write - 토론 게시하기");

        String work_title = service.getWorkTitle(workNum);

        model.addAttribute("work_num", workNum);
        model.addAttribute("work_title", work_title);
        return "forum/write";

    }
    @GetMapping("/forum/update")
    public String updateForum(ForumVO vo, Model model) {
        log.info("/forum/update - 토론 수정하기");

        ForumVO vo2 = service.selectOne(vo);
        log.info("vo2:{}", vo2);

        model.addAttribute("vo2", vo2);
        return "forum/update";
    }
    @PostMapping("/forum/insertOK")
    public String insertForumOK(ForumVO vo) {
        boolean result = service.insertForumOK(vo);
        if (result) {
            return "redirect:/forum/list";
        } else {
            return "forum/write";
        }
    }
    @PostMapping("/forum/updateOK")
    public String updateForumOK(ForumVO vo) {
        boolean result = service.updateForumOK(vo);
        if (result) {
            return "redirect:/forum/view?num=" + vo.getNum();
        } else {
            return "redirect:/forum/update?num=" + vo.getNum();
        }
    }
    @GetMapping("/forum/deleteOK")
    public String deleteForumOK(ForumVO vo) {
        boolean result = service.deleteForumOK(vo);
        if (result) {
            return "redirect:/forum/list";
        } else {
            return "redirect:/forum/view?num=" + vo.getNum();
        }
    }
//    DML 끝
}