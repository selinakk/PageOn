package com.tmtb.pageon.Book.controller;

import com.tmtb.pageon.Book.model.BookVO;
import com.tmtb.pageon.Book.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
public class BookController {

    @Autowired
    private BookService service;

    // 목록 조회 (카테고리 및 검색 조건에 따라)
    @GetMapping("/books")
    public String selectAllBooks(Model model,
                                 @RequestParam(required = false) String category,
                                 @RequestParam(required = false, defaultValue = "1") int cpage,
                                 @RequestParam(required = false, defaultValue = "20") int pageBlock,
                                 @RequestParam(required = false) String searchKey,
                                 @RequestParam(required = false) String searchWord) {
        log.info("selectAllBooks() category: {}, cpage: {}, pageBlock: {}, searchKey: {}, searchWord: {}",
                category, cpage, pageBlock, searchKey, searchWord);

        List<BookVO> list;
        int totalRows;

        // 검색어가 있을 때 (전체 조회 또는 카테고리 내 검색)
        if (searchWord != null && !searchWord.isEmpty()) {
            if (category != null && !category.equals("전체") && !category.isEmpty()) {
                // 5. 카테고리 내에서 검색 (Title or Writer)
                list = service.searchBooksInCategory(category, searchKey, searchWord, cpage, pageBlock);
                totalRows = service.getSearchTotalRowsInCategory(category, searchKey, searchWord);
            } else {
                // 2. 전체 조회에서 검색 (Title or Writer)
                list = service.searchBooks(searchKey, searchWord, cpage, pageBlock);
                totalRows = service.getSearchTotalRows(searchKey, searchWord);
            }
        } else if (category != null && !category.equals("전체") && !category.isEmpty()) {
            // 4. 카테고리 조회
            list = service.selectBooksByCategory(category, cpage, pageBlock);
            totalRows = service.getTotalRowsByCategory(category);
        } else {
            // 1. 전체 조회
            list = service.selectAllBooks(cpage, pageBlock);
            totalRows = service.getTotalRows();
        }

        int totalPageCount = (int) Math.ceil((double) totalRows / pageBlock);

        model.addAttribute("list", list);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("category", category);
        model.addAttribute("searchKey", searchKey);
        model.addAttribute("searchWord", searchWord);

        return "book/list";
    }

    // 상세 조회
    @GetMapping("/book/detail")
    public String selectOne(BookVO vo, Model model) {
        log.info("/book/detail");
        log.info("vo:{}", vo);

        // 현재 책 정보 조회
        BookVO vo2 = service.selectOne(vo);
        log.info("vo2:{}", vo2);

        model.addAttribute("vo2", vo2);

        // 동일한 카테고리의 유사한 책 5개만 조회
        List<BookVO> list = service.getLimitedBooksByCategory(vo2.getCategories(), 5, vo2.getItem_id());
        model.addAttribute("list", list);

        return "book/detail";
    }

}
