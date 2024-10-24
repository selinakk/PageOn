package com.tmtb.pageon.webtoon.controller;

import com.tmtb.pageon.board.model.BoardVO;
import com.tmtb.pageon.board.service.BoardService;
import com.tmtb.pageon.webtoon.model.WebtoonVO;
import com.tmtb.pageon.webtoon.service.WebtoonService;
import jakarta.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class WebtoonController {

    @Autowired
    private WebtoonService webtoonService;

    @Autowired
    ServletContext context;

    @GetMapping("/wt_selectAll")
    public String wt_selectAll(@RequestParam(defaultValue = "1") int page, Model model) {
        log.info("웹툰 전체 목록");

        int pageSize = 12;
        int totalCount = webtoonService.getTotalCount();
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        List<WebtoonVO> webtoonList;
        List<WebtoonVO> categories = webtoonService.getCategories();

        webtoonList = webtoonService.getWebtoonList(page, pageSize);


        // 페이지 번호 그룹화
        int pageGroupSize = 10;
        int currentPageGroup = (page - 1) / pageGroupSize;
        int startPage = currentPageGroup * pageGroupSize + 1;
        int endPage = Math.min(startPage + pageGroupSize - 1, totalPages);

        model.addAttribute("webtoonList", webtoonList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("categories", categories);
        log.info("categories:{}", categories);

        return "webtoon/selectAll";


    }


    @GetMapping("/wt_search")
    public String wt_search(@RequestParam(required = true) String searchWord,
                            @RequestParam String searchType,
                            @RequestParam(defaultValue = "1") int page,
                            Model model) {
        log.info("웹툰 검색 페이지");

        int pageSize = 12;
        int offset = (page - 1) * pageSize;

        List<WebtoonVO> webtoonList;
        List<WebtoonVO> categories = webtoonService.getCategories();
        int totalCount;
        // 제목 검색
        if ("title".equals(searchType)) {
            webtoonList = webtoonService.searchWebtoonByTitle(searchWord, offset, pageSize);
            totalCount = webtoonService.getTotalCountByTitle(searchWord);

            //작가 검색
        } else if ("writer".equals(searchType)) {
            webtoonList = webtoonService.searchWebtoonWriter(searchWord, offset, pageSize);
            totalCount = webtoonService.getTotalCountByContent(searchWord);
            //장르 검색
        } else if ("categories".equals(searchType)) {
            webtoonList = webtoonService.searchWebtoonByCategories(searchWord, offset, pageSize);
            totalCount = webtoonService.getTotalCountByCategories(searchWord);
        } else {
            // 기본 검색 타입이 없을 경우 제목으로 검색
            webtoonList = webtoonService.searchWebtoonByTitle(searchWord, offset, pageSize);
            totalCount = webtoonService.getTotalCountByTitle(searchWord);
        }

        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        model.addAttribute("webtoonList", webtoonList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("searchType", searchType);
        model.addAttribute("startPage", (page - 1) / 10 * 10 + 1);
        model.addAttribute("endPage", Math.min((page - 1) / 10 * 10 + 10, totalPages));
        model.addAttribute("categories", categories);

        return "webtoon/selectAll";
    }

    //게시글 상세 보기
    @GetMapping("/wt_selectOne")
    public String wt_selectOne(WebtoonVO vo, Model model) {
        log.info("게시글 상세보기 페이지");


        WebtoonVO vo2 = webtoonService.selectOne(vo);
        log.info("vo2:{}", vo2);

        model.addAttribute("vo2", vo2);

        return "webtoon/selectOne";
    }

    //필터링
    @GetMapping("/wt_filter")
    @ResponseBody
    public Map<String, Object> filterByCategories(@RequestParam(value = "categories", required = false) List<String> categories, @RequestParam(defaultValue = "1") int page) {
        log.info("카테고리 필터링: {}, 페이지: {}", categories, page);

        int pageSize = 12;
        int offset = (page - 1) * pageSize;

        List<WebtoonVO> webtoons;
        int totalCount;
        if (categories == null || categories.isEmpty()) {
            webtoons = webtoonService.getWebtoonList(page, pageSize);
            totalCount = webtoonService.getTotalCount();
        } else {
            webtoons = webtoonService.filterByCategories(categories, offset, pageSize);
            totalCount = webtoonService.getTotalCountByFilteredCategories(categories);
        }
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        Map<String, Object> response = new HashMap<>();
        response.put("webtoons", webtoons);
        response.put("totalPages", totalPages);

        return response;
    }


}
