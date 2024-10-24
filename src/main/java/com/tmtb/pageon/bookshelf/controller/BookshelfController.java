package com.tmtb.pageon.bookshelf.controller;

import com.tmtb.pageon.bookshelf.model.BookshelfVO;
import com.tmtb.pageon.bookshelf.service.BookshelfService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class BookshelfController {

    @Autowired
    BookshelfService service;

    //    목록 시작
    @GetMapping("/bookshelf/list")
    public String bookshelfList(Model model,
                            @RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(defaultValue = "date_added") String sortField,
                            @RequestParam(defaultValue = "desc") String sortDir,
                                @RequestParam(value="sort", defaultValue="all") String sort,
                                @RequestParam(value="userId") String userId,
                                HttpSession session
    ) {
        List<BookshelfVO> list;
        int totalList;

        //전체보기 또는 분류별로보기 + 페이징 분기
        if(sort.equals("all")){
            list = service.getList(page, size, sortField, sortDir, userId);
            totalList = service.getListCnt(userId);
        } else{
            list = service.getListBySort(sort, page, size, sortField, sortDir,userId);
            totalList = service.getListBySortCnt(sort, userId);
        }
        int totalPages = (int)Math.ceil((double)totalList/size);
        if (totalPages < 1) {totalPages = 0;}

        //파라미터 userId와 로그인id가 일치하는지 판별
        String loggedInUserId = "tester2";
//        String loggedInUserId = (String) session.getAttribute("loggedInUserId"); 세션의 로그인id 정보가 들어가야함
        boolean isOwner = loggedInUserId != null && loggedInUserId.equals(userId);
        log.info("로그인 아이디: {}, 서재 주인 아이디: {}",loggedInUserId, userId);

        //userId로 회원의 name 값 구하기
        String userName = service.getUserName(userId);

        model.addAttribute("list", list);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalList", totalList);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("currentSort", sort);
        model.addAttribute("isOwner", isOwner);
        model.addAttribute("userId", userId);
        model.addAttribute("userName", userName);

        log.info("/bookshelf/list - {}님의 서재 목록 페이지", userName);
        return "bookshelf/list";
    }
    //목록 끝
    //DML 시작
    @GetMapping("/bookshelf/write")
    public String bookshelfWrite(Model model){
        return "bookshelf/write";
    }
    @PostMapping("/bookshelf/insertOK")
    public String insertBookshelf(@RequestParam("user_id") String userId,
                                  @RequestParam("sort") String sort,
                                  @RequestParam("work_num") int workNum,
                                  RedirectAttributes redirectAttributes){
        log.info("/bookshelf/insertOK - 서재에 작품 추가");

        boolean result = service.insertBookshelfOK(userId, sort, workNum);
        if (result) {
            redirectAttributes.addFlashAttribute("successMsg", "서재에 추가되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "다시 시도해 주세요.");
        }
        return "redirect:/bookshelf/list?userId=tester2"; //임시 - 프로필id 필요
    }
    @PostMapping("/bookshelf/updateSortOK")
    public String updateSort(@RequestParam("sort") String sort,
                             @RequestParam("num") int num,
                             HttpSession session
                            ) {
        log.info("/bookshelf/updateSortOK - 서재 분류 변경");
        service.updateSortOK(sort, num);
        return "redirect:/bookshelf/list?userId=tester2"; //임시 - 프로필id 필요
    }
    @GetMapping("/bookshelf/deleteOK")
    public String deleteBookshelf(@RequestParam("num") int num,
                                  RedirectAttributes redirectAttributes,
                                  HttpSession session) {
        log.info("/bookshelf/deleteOK - 서재에서 작품 삭제");
        boolean result = service.deleteBookshelfOK(num);
        if (result) {
            redirectAttributes.addFlashAttribute("successMsg", "삭제되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "삭제 실패했습니다. 다시 시도해 주세요!");
        }
        return "redirect:/bookshelf/list?userId=tester2"; //임시 - 프로필id 필요
    }
    //DML 끝
}
