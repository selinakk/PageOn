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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        webtoonList = webtoonService.getWebtoonList(page, pageSize);


        model.addAttribute("webtoonList", webtoonList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

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
        int totalCount;
        if ("title".equals(searchType)) {
            webtoonList = webtoonService.searchWebtoonByTitle(searchWord, offset, pageSize);
            totalCount = webtoonService.getTotalCountByTitle(searchWord);
        } else if ("writer".equals(searchType)) {
            webtoonList = webtoonService.searchWebtoonWriter(searchWord, offset, pageSize);
            totalCount = webtoonService.getTotalCountByContent(searchWord);
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
            totalCount = webtoonService.getTotalCountByCategories(categories);
        }
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        Map<String, Object> response = new HashMap<>();
        response.put("webtoons", webtoons);
        response.put("totalPages", totalPages);

        return response;
    }

}
