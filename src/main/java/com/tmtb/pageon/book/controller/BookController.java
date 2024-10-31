package com.tmtb.pageon.book.controller;

import com.tmtb.pageon.book.model.BookVO;
import com.tmtb.pageon.book.service.BookService;
import com.tmtb.pageon.user.service.ProductService;
import jakarta.servlet.http.HttpSession;
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
public class BookController {

    @Autowired
    private BookService service;

    @Autowired
    private ProductService productService; // 캐싱을위한 product service생성

    // 목록 조회 (카테고리 및 검색 조건에 따라)
    @GetMapping("/books")
    public String selectAllBooks(Model model,
                                 @RequestParam(required = false) List<String> category, // 변경
                                 @RequestParam(required = false, defaultValue = "1") int cpage,
                                 @RequestParam(required = false, defaultValue = "20") int pageBlock,
                                 @RequestParam(required = false) String searchKey,
                                 @RequestParam(required = false) String searchWord,
                                 @RequestParam(required = false, defaultValue = "latest") String sortOrder) {
        log.info("selectAllBooks() category: {}, cpage: {}, pageBlock: {}, searchKey: {}, searchWord: {}, sortOrder: {}",
                category, cpage, pageBlock, searchKey, searchWord, sortOrder);

        List<BookVO> list;
        int totalRows;

        if (searchWord != null && !searchWord.isEmpty()) {
            if (category != null && !category.isEmpty()) {
                list = service.searchBooksInCategories(category, searchKey, searchWord, cpage, pageBlock, sortOrder);
                totalRows = service.getSearchTotalRowsInCategories(category, searchKey, searchWord);
            } else {
                list = service.searchBooks(searchKey, searchWord, cpage, pageBlock, sortOrder);
                totalRows = service.getSearchTotalRows(searchKey, searchWord);
            }
        } else if (category != null && !category.isEmpty()) {
            list = service.selectBooksByCategories(category, cpage, pageBlock, sortOrder);
            totalRows = service.getTotalRowsByCategories(category);
        } else {
            list = service.selectAllBooks(cpage, pageBlock, sortOrder);
            totalRows = service.getTotalRows();
        }

        int totalPageCount = (int) Math.ceil((double) totalRows / pageBlock);

        model.addAttribute("list", list);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("category", category);
        model.addAttribute("searchKey", searchKey);
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("sortOrder", sortOrder);

        return "book/list";
    }

    // 상세 조회
    // 상세 조회
    @GetMapping("/book/detail")
    public String selectOne(BookVO vo, Model model, HttpSession session) {
        log.info("/book/detail");
        log.info("vo: {}", vo);

        // 현재 책 정보 조회
        BookVO vo2 = service.selectOne(vo);
        log.info("vo2: {}", vo2);

        // 세션에서 사용자 ID 가져오기
        String id = (String) session.getAttribute("id");
        log.info("세션에서 가져온 사용자 ID: {}", id);

        // 최근 본 항목 추가
        List<Object> recentItems = productService.addRecentItem(id,vo2); // 캐싱을 위한 product service 생성 작성자:김규년
        log.info("최근 본 항목 리스트: {}", recentItems);

        model.addAttribute("vo2", vo2);

        // 동일한 카테고리의 유사한 책 조회
        List<BookVO> list = service.getLimitedBooksByCategory(vo2.getCategories(), 20, vo2.getItem_id());
        model.addAttribute("list", list);

        log.info("동일한 카테고리의 유사한 책 리스트: {}", list);

        return "book/detail";
    }
    //  // 세션에서 사용자 ID 가져오기
    //        String id = (String) session.getAttribute("id");
    //        log.info("세션에서 가져온 사용자 ID: {}", id);
    //
    //        List<Object> recentItems = productService.addRecentItem(id,vo2); // 캐싱을 위한 product service 생성
    //        log.info("최근 본 항목 리스트: {}", recentItems);
    //이 내용은 사용자 최근 조회 목록을 위해 싱세조회api가 호출할때 발생하는 vo데이터들을 캐싱하기 위해서 짠 로직입니다
    //여러번 테스트를 해본결과 해당 기능에 문제가 없고 최근조회목록 조회기능에도 문제가 없는거 같습니다 만약 문제가 생긴다면 연락부탁드립니다.


    // 내 서재에 추가 (읽은 작품, 읽고 있는 작품, 읽고 싶은 작품)
    // added_bs 추가 테스트를 위해 넣어두었음 추후 서재쪽 패키지 확인하고 수정 예정
    @PostMapping("/book/addToLibrary")
    public String addToLibrary(@RequestParam int item_id, @RequestParam String status) {
        log.info("addToLibrary() item_id: {}, status: {}", item_id, status);

        // 해당 작품의 added_bs 값을 증가시킴
        service.updateAddedBs(item_id);

        // 상태에 따라 추가 로직을 처리할 수 있음 (필요에 따라 확장 가능)
        if ("read".equals(status)) {
            log.info("읽은 작품에 추가");
        } else if ("reading".equals(status)) {
            log.info("읽고 있는 작품에 추가");
        } else if ("wantToRead".equals(status)) {
            log.info("읽고 싶은 작품에 추가");
        }

        return "redirect:/book/detail?item_id=" + item_id; // 상세 페이지로 리다이렉트
    }

}
