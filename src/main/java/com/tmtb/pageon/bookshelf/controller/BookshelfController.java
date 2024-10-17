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
                            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        List<BookshelfVO> list = service.getList(page, size, sortField, sortDir);
        int totalList = service.getListCount();
        int totalPages = (int)Math.ceil((double)totalList/size);

        model.addAttribute("list", list);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalList", totalList);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        log.info("/bookshelf/list - 서재 목록 페이지");

        return "bookshelf/list";
    }
    @PostMapping("/book/updateSortOK")
    public String updateSort(@RequestParam("sort") String sort, @RequestParam("num") int num) {
        service.updateSortOK(sort, num);
        return "redirect:/bookshelf/list";
    }
    //목록 끝
}
