package com.tmtb.pageon.forum.controller;

import com.tmtb.pageon.forum.model.ForumVO;
import com.tmtb.pageon.forum.service.ForumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
public class ForumController {

    @Autowired
    ForumService service;

//    목록 시작
    @GetMapping("/forum/list")
    public String forumList(Model model,
                            @RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "4") int size,
                            @RequestParam(defaultValue = "wdate") String sortField,
                            @RequestParam(defaultValue = "desc") String sortDir
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
        int totalSearchList = service.searchForumCount(searchKey, searchWord);
        int totalSearchPages = (int) Math.ceil((double) totalSearchList / size);

        model.addAttribute("list", list);
        model.addAttribute("searchKey", searchKey);
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("totalSearchList", totalSearchList);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalSearchPages", totalSearchPages);

        log.info("/forum/search - 토론 목록 검색 실행");
        return "forum/list";
    }
//    목록 끝
//    상세보기 시작
    @GetMapping("/forum/view")
    public String forumView(ForumVO vo, Model model) {
        ForumVO vo2 = service.selectOne(vo);

        model.addAttribute("vo2", vo2);
        log.info("/forum/view - 토론 상세보기 {}", vo2);
        return "forum/view";
    }
//    상세보기 끝
//    DML 시작
    @GetMapping("/forum/write")
    public String insertForum() {
        log.info("/forum/write - 토론 게시하기");
        return "forum/write";

    }
    @GetMapping("/forum/update")
    public String updateForum() {

        log.info("/forum/update - 토론 수정하기");
        return "forum/update";
    }
    @PostMapping("/forum/insertOK.do")
    public String insertForumOK(ForumVO vo) {
        boolean result = service.insertForumOK(vo);
        if (result) {
            return "redirect:/forum/list";
        } else {
            return "forum/write";
        }
    }
//    DML 끝
}
