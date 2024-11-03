package com.tmtb.pageon;

import com.tmtb.pageon.board.model.BoardVO;
import com.tmtb.pageon.book.model.BookVO;
import com.tmtb.pageon.book.service.BookService;
import com.tmtb.pageon.community.service.CommunityService;
import com.tmtb.pageon.forum.model.ForumVO;
import com.tmtb.pageon.notice.model.NoticeVO;
import com.tmtb.pageon.user.model.ReviewVO;
import com.tmtb.pageon.webnovel.model.WebnovelVO;
import com.tmtb.pageon.webnovel.service.WebnovelService;
import com.tmtb.pageon.webtoon.model.WebtoonVO;
import com.tmtb.pageon.webtoon.service.WebtoonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

        // 인기순으로 20개의 웹소설을 조회
        List<WebnovelVO> popularWebnovels = webnovelService.selectPopularWebnovels(1, 20);
        model.addAttribute("popularWebnovels", popularWebnovels);

        // 인기순으로 20개의 도서를 조회
        List<BookVO> popularBooks = bookService.selectPopularBooks(1, 20);
        model.addAttribute("popularBooks", popularBooks);

        return "work/work_index";
    }
}
