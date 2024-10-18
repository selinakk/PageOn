package com.tmtb.pageon.bookshelf.controller;

import com.tmtb.pageon.bookshelf.model.BookshelfVO;
import com.tmtb.pageon.bookshelf.service.BookshelfService;
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
                                @RequestParam(value="sort", defaultValue="all") String sort
    ) {
        List<BookshelfVO> list;
        int totalList;

        if(sort.equals("all")){
            list = service.getList(page, size, sortField, sortDir);
            totalList = service.getListCnt();

        } else{
            list = service.getListBySort(sort, page, size, sortField, sortDir);
            totalList = service.getListBySortCnt(sort);
        }
        int totalPages = (int)Math.ceil((double)totalList/size);
        if (totalPages < 1) {
            totalPages = 0;
        }
        model.addAttribute("list", list);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalList", totalList);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("currentSort", sort);
        log.info("/bookshelf/list - 서재 목록 페이지");

        return "bookshelf/list";
    }
    //목록 끝
    //DML 시작
    @PostMapping("/book/updateSortOK")
    public String updateSort(@RequestParam("sort") String sort, @RequestParam("num") int num) {
        service.updateSortOK(sort, num);
        return "redirect:/bookshelf/list";
    }
    @GetMapping("/bookshelf/deleteOK")
    public String deleteBookshelf(@RequestParam("num") int num,
                                  RedirectAttributes redirectAttributes) {
        boolean result = service.deleteBookshelfOK(num);
        if (result) {
            redirectAttributes.addFlashAttribute("successMsg", "삭제되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "삭제 실패했습니다. 다시 시도해 주세요!");
        }
        return "redirect:/bookshelf/list";
    }
    //DML 끝
}
