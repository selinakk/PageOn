package com.tmtb.pageon.webtoon.controller;

import com.tmtb.pageon.board.model.BoardVO;
import com.tmtb.pageon.board.service.BoardService;
import com.tmtb.pageon.forum.model.ForumVO;
import com.tmtb.pageon.forum.service.ForumService;
import com.tmtb.pageon.review.model.ReviewVO;
import com.tmtb.pageon.review.service.ReviewService;
import com.tmtb.pageon.user.model.UserVO;
import com.tmtb.pageon.user.service.ProductService;
import com.tmtb.pageon.user.service.UserService;
import com.tmtb.pageon.webtoon.model.WebtoonVO;
import com.tmtb.pageon.webtoon.service.WebtoonService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    ServletContext context;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ForumService forumService;


    @GetMapping("/wt_selectAll")
    public String wt_selectAll(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(required = false) String sortOrder,
                               Model model, HttpSession session) {
        log.info("웹툰 전체 목록");

        int pageSize = 20;
        int totalCount = webtoonService.getTotalCount();
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        List<WebtoonVO> webtoonList;
        List<WebtoonVO> allCategories = webtoonService.getCategories();

        webtoonList = webtoonService.getWebtoonList(page, pageSize);

        // 세션에서 사용자 ID 가져오기
        String id = (String) session.getAttribute("id");
        log.info("세션에서 가져온 사용자 ID: {}", id);

        List<String> likeCategories = new ArrayList<>();
        if (id != null) {
            // id로 사용자 정보 조회 후 like_categories 가져오기
            UserVO user = userService.findById(id);
            if (user.getLike_categories() != null && !user.getLike_categories().isEmpty()) {
                likeCategories = Arrays.asList(user.getLike_categories().split(",\\s*"));
            }
        }

        // 페이지 번호 그룹화
        int pageGroupSize = 10;
        int currentPageGroup = (page - 1) / pageGroupSize;
        int startPage = currentPageGroup * pageGroupSize + 1;
        int endPage = Math.min(startPage + pageGroupSize - 1, totalPages);

        model.addAttribute("likeCategories", likeCategories);
        model.addAttribute("webtoonList", webtoonList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("allCategories", allCategories);
        model.addAttribute("sortOrder", sortOrder);
        log.info("allCategories:{}", allCategories);
        log.info("likeCategories: {}", likeCategories);

        return "webtoon/selectAll";
    }


    @GetMapping("/wt_search")
    public String wt_search(@RequestParam(required = false) String searchWord,
                            @RequestParam String searchType,
                            @RequestParam(defaultValue = "1") int page,
                            @RequestParam(required = false) List<String> categories,
                            Model model) {
        log.info("웹툰 검색 페이지");

        int pageSize = 20;
        int offset = (page - 1) * pageSize;

        List<WebtoonVO> webtoonList;
        List<WebtoonVO> allCategories = webtoonService.getCategories();
        int totalCount;
        // 제목 검색
        if ("title".equals(searchType)) {
            webtoonList = webtoonService.searchWebtoonByTitle(searchWord, offset, pageSize);
            totalCount = webtoonService.getTotalCountByTitle(searchWord);
            //작가 검색
        } else if ("writer".equals(searchType)) {
            webtoonList = webtoonService.searchWebtoonByWriter(searchWord, offset, pageSize);
            totalCount = webtoonService.getTotalCountByContent(searchWord);
            //장르 검색
        } else if ("categories".equals(searchType)) {
            webtoonList = webtoonService.searchMultiCategories(categories, offset, pageSize);
            totalCount = webtoonService.getTotalCountByMultiCategories(categories);
        } else if ("popular".equals(searchType)) {
            webtoonList = webtoonService.selectPopularWebtoons(offset, pageSize);
            totalCount = webtoonService.getTotalCountByPopular();
        } else {
            // 기본 검색 타입이 없을 경우 가나다순
            webtoonList = webtoonService.getWebtoonList(page, pageSize);
            totalCount = webtoonService.getTotalCount();
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
        model.addAttribute("allCategories", allCategories);

        log.info("searchWord:{}", searchWord);
        log.info("categories:{}", categories);
        log.info("searchType:{}", searchType);

        return "webtoon/selectAll";
    }

    @GetMapping("/wt_like")
    public String searchLikeCategories(@RequestParam(defaultValue = "1") int page, @RequestParam(required = false) List<String> categories, Model model, HttpSession session) {

        int pageSize = 20;
        int offset = (page - 1) * pageSize;
        int totalCount;


        List<WebtoonVO> allCategories = webtoonService.getCategories();

        // 세션에서 사용자 ID 가져오기
        String id = (String) session.getAttribute("id");
        log.info("세션에서 가져온 사용자 ID: {}", id);

        // id로 사용자 정보 조회 후 like_categories 가져오기
        UserVO user = userService.findById(id);
        List<String> likeCategories = Arrays.asList(user.getLike_categories().split(",\\s*"));
        log.info("likeCategories: {}", likeCategories);
        model.addAttribute("likeCategories", likeCategories);

        String filter = "prefer";
        model.addAttribute("filter", filter);


        List<WebtoonVO> webtoonList;

        webtoonList = webtoonService.searchLikeCategories(likeCategories, offset, pageSize);
        totalCount = webtoonService.getTotalCountByLikeCategories(likeCategories);
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        model.addAttribute("webtoonList", webtoonList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startPage", (page - 1) / 10 * 10 + 1);
        model.addAttribute("endPage", Math.min((page - 1) / 10 * 10 + 10, totalPages));
        model.addAttribute("categories", categories);
        model.addAttribute("allCategories", allCategories);

        return "webtoon/selectAll";
    }

    // 게시글 상세 보기
    @GetMapping("/wt_selectOne")
    public String wt_selectOne(WebtoonVO vo, Model model, HttpSession session) {
        log.info("게시글 상세보기 페이지");

        // 세션에서 사용자 ID 가져오기
        String id = (String) session.getAttribute("id");
        log.info("세션에서 가져온 사용자 ID: {}", id);

        WebtoonVO vo2 = webtoonService.selectOne(vo);
        List<WebtoonVO> similarWebtoons = webtoonService.searchWebtoonByCategories(vo2.getCategories(), 0, 15);

        // 최근 본 항목 추가 (userId를 함께 전달)
        List<Object> recentItems = productService.addRecentItem(id, vo2); // 캐싱을 위한 product service 생성
        log.info("최근 본 항목 리스트: {}", recentItems);

        String formattedUpdateDay = vo2.getFormattedUpdateDay();
        log.info("vo2:{}", vo2);
        log.info("similarWebtoons:{}", similarWebtoons);

        String webtoonTitle = vo2.getTitle();

        List<ReviewVO> reviewList = reviewService.searchListPageBlock("workTitle", webtoonTitle, 1, 20);
        List<ForumVO> forumList = forumService.searchForum("workTitle", webtoonTitle, 1, 20);

        model.addAttribute("reviewList", reviewList);
        model.addAttribute("forumList", forumList);
        model.addAttribute("vo2", vo2);
        model.addAttribute("similarWebtoons", similarWebtoons);
        model.addAttribute("recentItems", recentItems); // 최근 본 항목 추가

        return "webtoon/selectOne";
    }


    //리뷰 작성 시 카테고리 추천
    @GetMapping("/webtoonrecommendation")
    public String webtoonrecommendation(HttpSession session, Model model, @RequestParam(defaultValue = "1") int page) {
        // 사용자 id 가져오기
        String id = (String) session.getAttribute("id");
        int pageSize = 20;
        int offset = (page - 1) * pageSize;
        int totalCount = webtoonService.webtoonGetRecommandationTotalCount(id);
        List<WebtoonVO> webtoonList = webtoonService.getWebtoonRecommendationBycategory(id, offset, pageSize);

        // 페이지 번호 그룹화
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);
        int pageGroupSize = 10;
        int currentPageGroup = (page - 1) / pageGroupSize;
        int startPage = currentPageGroup * pageGroupSize + 1;
        int endPage = Math.min(startPage + pageGroupSize - 1, totalPages);

        model.addAttribute("webtoonList", webtoonList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("filter", "recommend");

        // 페이지네이션 링크 생성
        String baseUrl = "/webtoonrecommendation?page=";
        model.addAttribute("baseUrl", baseUrl);

        return "webtoon/selectAll";
    }


}
