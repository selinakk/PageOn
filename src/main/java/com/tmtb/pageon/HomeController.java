package com.tmtb.pageon;

import com.tmtb.pageon.book.model.BookVO;
import com.tmtb.pageon.book.service.BookService;
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
