package com.tmtb.pageon.community.controller;

import com.tmtb.pageon.board.model.BoardVO;
import com.tmtb.pageon.notice.model.NoticeVO;
//import com.tmtb.pageon.review.model.ReviewVO;
import com.tmtb.pageon.community.model.CommunityVO;
import com.tmtb.pageon.community.service.CommunityService;
import com.tmtb.pageon.forum.model.ForumVO;
import com.tmtb.pageon.user.model.ReviewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Slf4j
@Controller
public class CommunityController {

    @Autowired
    CommunityService service;


    // 커뮤니티 페이지
    @GetMapping("/community")
    public String communityMain(Model model) {
        log.info("커뮤니티 페이지");

        //최근 3개의 게시글, 공지사항글, 리뷰글, 토론글 불러오기
        List<BoardVO> list1 = service.boardCommunity();
        List<NoticeVO> list2 = service.noticeCommunity();
        List<ReviewVO> list3 = service.reviewCommunity();
        List<ForumVO> list4 = service.forumCommunity();

        log.info("list1.size():{}", list1.size());
        log.info("list2.size():{}", list2.size());
        log.info("list3.size():{}", list3.size());
        log.info("list4.size():{}", list4.size());

        model.addAttribute("list1", list1);
        model.addAttribute("list2", list2);
        model.addAttribute("list3", list3);
        model.addAttribute("list4", list4);

        return "community/community_main";
    }


    //공지사항 상세 조회는 notice에 있음


    // 게시글 상세 조회
    @GetMapping("/board/b_selectOne.do")
    public String selectOne(BoardVO vo, Model model) {
        log.info("게시글 상세 조회");
        log.info("vo:{}", vo);

        BoardVO vo2 = service.boardSelectOne(vo);
        log.info("vo2:{}", vo2);

        model.addAttribute("vo2", vo2);

        return "community/community_main";
    }



    // 리뷰글 상세 조회
    @GetMapping("/review/r_selectOne.do")
    public String selectOne(ReviewVO vo, Model model) {
        log.info("리뷰글 상세 조회");
        log.info("vo:{}", vo);

        ReviewVO vo2 = service.reviewSelectOne(vo);
        log.info("vo2:{}", vo2);

        model.addAttribute("vo2", vo2);

        return "community/community_main";
    }



    // 토론글 상세 조회
    @GetMapping("/forum/f_selectOne.do")
    public String selectOne(ForumVO vo, Model model) {
        log.info("토론글 상세 조회");
        log.info("vo:{}", vo);

        ForumVO vo2 = service.forumSelectOne(vo);
        log.info("vo2:{}", vo2);

        model.addAttribute("vo2", vo2);

        return "community/community_main";
    }
}