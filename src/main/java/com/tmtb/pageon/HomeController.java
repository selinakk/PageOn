package com.tmtb.pageon;

import com.tmtb.pageon.board.model.BoardVO;
import com.tmtb.pageon.book.controller.BookController;
import com.tmtb.pageon.book.model.BookVO;
import com.tmtb.pageon.book.service.BookService;
import com.tmtb.pageon.community.service.CommunityService;
import com.tmtb.pageon.forum.model.ForumVO;
import com.tmtb.pageon.notice.model.NoticeVO;
import com.tmtb.pageon.review.service.ReviewService;
import com.tmtb.pageon.user.model.ReviewVO;
import com.tmtb.pageon.user.model.UserVO;
import com.tmtb.pageon.user.service.UserService;
import com.tmtb.pageon.webnovel.controller.WebnovelController;
import com.tmtb.pageon.webnovel.model.WebnovelVO;
import com.tmtb.pageon.webnovel.service.WebnovelService;
import com.tmtb.pageon.webtoon.model.WebtoonVO;
import com.tmtb.pageon.webtoon.service.WebtoonService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
public class HomeController {

    @Autowired
    private WebnovelService webnovelService;

    @Autowired
    private BookService bookService;

    @Autowired
    private WebtoonService webtoonService;

    @Autowired
    CommunityService service;

    @Autowired
    private UserService userService;
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/")
    public String home(Model model) {
        log.info("인덱스 페이지");

        // 인기순으로 20개의 웹소설을 조회
        List<WebnovelVO> popularWebnovels = webnovelService.selectPopularWebnovels(1, 20);
        model.addAttribute("popularWebnovels", popularWebnovels);

        // 인기순으로 20개의 도서를 조회
        List<BookVO> popularBooks = bookService.selectPopularBooks(1, 20);
        model.addAttribute("popularBooks", popularBooks);

        //인기순으로 20개의 웹툰을 조회
        List<WebtoonVO> popularWebtoons = webtoonService.selectPopularWebtoons(1, 20);
        model.addAttribute("popularWebtoons", popularWebtoons);


        // 홈화면에 최근 4개의 게시글 조회
        List<BoardVO> boardVO = service.boardSelectList();
        log.info("boardVO:{}", boardVO);

        // 홈화면에 최근 4개의 공지사항글 조회
        List<NoticeVO> noticeVO = service.noticeSelectList();
        log.info("noticeVO:{}", noticeVO);

        // 홈화면에 최근 4개의 리뷰글 조회
        List<ReviewVO> reviewVO = service.reviewSelectList();
        log.info("reviewVO:{}", reviewVO);

        // 홈화면에 최근 4개의 토론글 조회
        List<ForumVO> forumVO = service.forumSelectList();
        log.info("forumVO:{}", forumVO);

        model.addAttribute("boardVO", boardVO);
        model.addAttribute("noticeVO", noticeVO);
        model.addAttribute("reviewVO", reviewVO);
        model.addAttribute("forumVO", forumVO);

        return "index";
    }


    @GetMapping("/work_index")
    public String workPage(Model model) {
        log.info("작품 메인 페이지");

        // 테스트용 더미 사용자 ID
        String id = "admin11";
        UserVO user = userService.findById(id);
        List<String> likeCategories = Arrays.asList(user.getLike_categories().split(","));
        model.addAttribute("likeCategories", likeCategories);


        //사용자 추천 목록 3개만 조회
        List<BookVO> preferBooks= reviewService.getBookBycategories(id, 1, 4);
        log.info("preferBooks:{}", preferBooks);

        //사용자 추천 목록 3개만 조회
        List<WebtoonVO> preferWebtoons= reviewService.getWebtoonBycategories (id, 1, 5);
        log.info("preferWebtoons:{}", preferWebtoons);


        //사용자 추천 목록 3개만 조회
        List<WebnovelVO> preferWebnovels= reviewService.getWrbnovelBycategories (id, 1, 5);
        log.info("preferWebnovels:{}", preferWebnovels);





        // 선호 카테고리 기반 웹소설 목록 조회
        List<WebnovelVO> likedWebnovels = webnovelService.selectWebnovelsByCategories(likeCategories, 1, 20, "latest");
        model.addAttribute("likedWebnovels", likedWebnovels);

        // 선호 카테고리 기반 도서 목록 조회
        List<BookVO> likedBooks = bookService.selectBooksByCategories(likeCategories, 1, 20, "latest");
        model.addAttribute("likedBooks", likedBooks);

        // 인기순으로 20개의 웹툰 조회
        List<WebtoonVO> popularWebtoons = webtoonService.selectPopularWebtoons(1, 20);
        model.addAttribute("popularWebtoons", popularWebtoons);

        // 인기순으로 20개의 웹소설 조회
        List<WebnovelVO> popularWebnovels = webnovelService.selectPopularWebnovels(1, 20);
        model.addAttribute("popularWebnovels", popularWebnovels);

        // 인기순으로 20개의 도서 조회
        List<BookVO> popularBooks = bookService.selectPopularBooks(1, 20);
        model.addAttribute("popularBooks", popularBooks);

        return "work/work_index";
    }

}
