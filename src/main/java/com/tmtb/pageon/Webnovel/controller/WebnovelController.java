package com.tmtb.pageon.Webnovel.controller;

import com.tmtb.pageon.Webnovel.model.WebnovelVO;
import com.tmtb.pageon.Webnovel.service.WebnovelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
public class WebnovelController {

    @Autowired
    private WebnovelService service;

    // 목록 조회 (카테고리 및 검색 조건에 따라)
    @GetMapping("/webnovels")
    public String selectAllWebnovels(Model model,
                                 @RequestParam(required = false) String category,
                                 @RequestParam(required = false, defaultValue = "1") int cpage,
                                 @RequestParam(required = false, defaultValue = "20") int pageBlock,
                                 @RequestParam(required = false) String searchKey,
                                 @RequestParam(required = false) String searchWord) {
        log.info("selectAllWebnovels() category: {}, cpage: {}, pageBlock: {}, searchKey: {}, searchWord: {}",
                category, cpage, pageBlock, searchKey, searchWord);

        List<WebnovelVO> list;
        int totalRows;

        // 검색어가 있을 때 (전체 조회 또는 카테고리 내 검색)
        if (searchWord != null && !searchWord.isEmpty()) {
            if (category != null && !category.equals("전체") && !category.isEmpty()) {
                // 5. 카테고리 내에서 검색 (Title or Writer)
                list = service.searchWebnovelsInCategory(category, searchKey, searchWord, cpage, pageBlock);
                totalRows = service.getSearchTotalRowsInCategory(category, searchKey, searchWord);
            } else {
                // 2. 전체 조회에서 검색 (Title or Writer)
                list = service.searchWebnovels(searchKey, searchWord, cpage, pageBlock);
                totalRows = service.getSearchTotalRows(searchKey, searchWord);
            }
        } else if (category != null && !category.equals("전체") && !category.isEmpty()) {
            // 4. 카테고리 조회
            list = service.selectWebnovelsByCategory(category, cpage, pageBlock);
            totalRows = service.getTotalRowsByCategory(category);
        } else {
            // 1. 전체 조회
            list = service.selectAllWebnovels(cpage, pageBlock);
            totalRows = service.getTotalRows();
        }

        int totalPageCount = (int) Math.ceil((double) totalRows / pageBlock);

        model.addAttribute("list", list);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("category", category);
        model.addAttribute("searchKey", searchKey);
        model.addAttribute("searchWord", searchWord);

        return "webnovel/list";
    }

    // 상세 조회
    @GetMapping("/webnovel/detail")
    public String selectOne(WebnovelVO vo, Model model) {
        log.info("/webnovel/detail");
        log.info("vo:{}", vo);

        // 현재 책 정보 조회
        WebnovelVO vo2 = service.selectOne(vo);
        log.info("vo2:{}", vo2);

        model.addAttribute("vo2", vo2);

        // 동일한 카테고리의 유사한 책 5개만 조회
        List<WebnovelVO> list = service.getLimitedWebnovelsByCategory(vo2.getCategories(), 5, vo2.getItem_id());
        model.addAttribute("list", list);

        return "webnovel/detail";
    }

}
