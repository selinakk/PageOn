package com.tmtb.pageon.webnovel.controller;

import com.tmtb.pageon.user.service.ProductService;
import com.tmtb.pageon.webnovel.model.WebnovelVO;
import com.tmtb.pageon.webnovel.service.WebnovelService;
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
public class WebnovelController {

    @Autowired
    private WebnovelService service;

    @Autowired
    private ProductService productService;

    // 목록 조회 (카테고리 및 검색 조건에 따라)
    @GetMapping("/webnovels")
    public String selectAllWebnovels(Model model,
                                 @RequestParam(required = false) List<String> category, // 변경
                                 @RequestParam(required = false, defaultValue = "1") int cpage,
                                 @RequestParam(required = false, defaultValue = "20") int pageBlock,
                                 @RequestParam(required = false) String searchKey,
                                 @RequestParam(required = false) String searchWord,
                                 @RequestParam(required = false, defaultValue = "latest") String sortOrder) {
        log.info("selectAllWebnovels() category: {}, cpage: {}, pageBlock: {}, searchKey: {}, searchWord: {}, sortOrder: {}",
                category, cpage, pageBlock, searchKey, searchWord, sortOrder);

        List<WebnovelVO> list;
        int totalRows;

        if (searchWord != null && !searchWord.isEmpty()) {
            if (category != null && !category.isEmpty()) {
                list = service.searchWebnovelsInCategories(category, searchKey, searchWord, cpage, pageBlock, sortOrder);
                totalRows = service.getSearchTotalRowsInCategories(category, searchKey, searchWord);
            } else {
                list = service.searchWebnovels(searchKey, searchWord, cpage, pageBlock, sortOrder);
                totalRows = service.getSearchTotalRows(searchKey, searchWord);
            }
        } else if (category != null && !category.isEmpty()) {
            list = service.selectWebnovelsByCategories(category, cpage, pageBlock, sortOrder);
            totalRows = service.getTotalRowsByCategories(category);
        } else {
            list = service.selectAllWebnovels(cpage, pageBlock, sortOrder);
            totalRows = service.getTotalRows();
        }

        int totalPageCount = (int) Math.ceil((double) totalRows / pageBlock);

        model.addAttribute("list", list);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("category", category);
        model.addAttribute("searchKey", searchKey);
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("sortOrder", sortOrder);

        return "webnovel/list";
    }

    // 상세 조회
    @GetMapping("/webnovel/detail")
    public String selectOne(WebnovelVO vo, Model model, HttpSession session) {
        log.info("/webnovel/detail");
        log.info("vo:{}", vo);

        // 현재 책 정보 조회
        WebnovelVO vo2 = service.selectOne(vo);
        log.info("vo2:{}", vo2);
        // 세션에서 사용자 ID 가져오기
        String id = (String) session.getAttribute("id");
        log.info("세션에서 가져온 사용자 ID: {}", id);

        List<Object> recentItems = productService.addRecentItem(id,vo2); // 캐싱을 위한 product service 생성
        log.info("최근 본 항목 리스트: {}", recentItems);
        model.addAttribute("vo2", vo2);

        // 동일한 카테고리의 유사한 웹소설 조회
        List<WebnovelVO> list = service.getLimitedWebnovelsByCategory(vo2.getCategories(), 20, vo2.getItem_id());
        model.addAttribute("list", list);

        return "webnovel/detail";
    }
    //  // 세션에서 사용자 ID 가져오기
    //        String id = (String) session.getAttribute("id");
    //        log.info("세션에서 가져온 사용자 ID: {}", id);
    //
    //        List<Object> recentItems = productService.addRecentItem(id,vo2); // 캐싱을 위한 product service 생성
    //        log.info("최근 본 항목 리스트: {}", recentItems);
    //이 내용은 사용자 최근 조회 목록을 위해 조회할때 발생하난 vo데이터 들을 캐싱하기 위해서 짠 로직입니다
    //여러번 테스트 






    // 내 서재에 추가 (읽은 작품, 읽고 있는 작품, 읽고 싶은 작품)
    // added_bs 추가 테스트를 위해 넣어두었음 추후 서재쪽 패키지 확인하고 수정 예정
    @PostMapping("/webnovel/addToLibrary")
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

        return "redirect:/webnovel/detail?item_id=" + item_id; // 상세 페이지로 리다이렉트
    }

}
