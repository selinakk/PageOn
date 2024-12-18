package com.tmtb.pageon.review.conroller;


import com.tmtb.pageon.book.model.BookVO;
import com.tmtb.pageon.comment.controller.CommentController;
import com.tmtb.pageon.comment.model.CommentVO;
import com.tmtb.pageon.review.service.ReviewService;
import com.tmtb.pageon.review.service.SentimentAnalysisService;
import com.tmtb.pageon.review.model.ReviewVO;
import com.tmtb.pageon.webnovel.model.WebnovelVO;
import com.tmtb.pageon.webtoon.model.WebtoonVO;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class ReviewContoller {

    @Autowired
    ReviewService service;

    @Autowired
    CommentController controller;

    @Autowired
    SentimentAnalysisService sentimentservice;

    //리뷰 목록
    @GetMapping("/review/list")
    public String getUsers(Model model, @RequestParam(defaultValue = "1")int cpage,
                           @RequestParam(defaultValue ="4" )int pageBlock,
                           @RequestParam(defaultValue = "recent")String sortType
                           //@RequestParam String userId
    ) {
        log.info("리뷰 목록");
        List<ReviewVO> list = service.selectAllPageBlock(cpage, pageBlock, sortType );
        model.addAttribute("list", list);
        log.info("list:{}", list);

        int total_Row =service.getTotalRow();
        int totalPageCount =0;
        if (total_Row / pageBlock ==0){
            totalPageCount =1;
        } else if (total_Row % pageBlock ==0) {
            totalPageCount = total_Row / pageBlock;
        } else {
            totalPageCount = total_Row / pageBlock +1;
        }

        //총 리뷰 갯수
        model.addAttribute("total_Row", total_Row);
        log.info("totalRow:{}", total_Row);

        //페이지 수
        model.addAttribute("totalPageCount", totalPageCount);
        log.info("totalPageCount:{}", totalPageCount);

        //정렬순
        log.info("sortType:{}", sortType);
        log.info("review seleteAll cpage:{}, pageBlock:{}", cpage,pageBlock);
        model.addAttribute("cpage", cpage);
        model.addAttribute("sortType", sortType);



        return "review/list";
    }

    //리뷰 상세
    @GetMapping("/review/detail")
    public String selectOne(Model model,
                            HttpSession session,
                            ReviewVO vo,
                            @RequestParam(defaultValue = "1")int cpage,
                            @RequestParam(defaultValue = "20")int pageBlock){
        // 세션에서 사용자 ID 가져오기
        String id = (String) session.getAttribute("id");
        log.info("세션에서 가져온 사용자 ID: {}", id);

        log.info("리뷰 상세");
        ReviewVO vo2 = service.selectOne(vo);
        model.addAttribute("vo2", vo2);

        log.info("vo2:{}", vo2);

        //  CommentController의 selectAll() 메서드를 호출하여 댓글 데이터를 가져옵니다.
        Map<String, Object> commentsData = controller.selectAll("review", null, null, vo2.getNum(), cpage, pageBlock); // type과 부모글의

        // 댓글 목록 가져오기
        List<CommentVO> comments = (List<CommentVO>) commentsData.get("comments");

        // 전체 댓글 수 가져오기
        int totalRows = (int) commentsData.get("totalRows");

        model.addAttribute("totalPageCount", (int) Math.ceil((double) totalRows / pageBlock));
        model.addAttribute("comments", comments);
        model.addAttribute("cpage", cpage);
        model.addAttribute("pageBlock", pageBlock);
        model.addAttribute("vo2", vo2);
        return "review/detail";
    }
    //리뷰 검색 및 페이징
    @GetMapping("/review/searchList")
    public String searchList(Model model, ReviewVO vo, @RequestParam(defaultValue = "title")String searchKey,
                             @RequestParam(defaultValue = "book")String searchWord,
                             @RequestParam(defaultValue = "1")int cpage,
                             @RequestParam(defaultValue = "4")int pageBlock){
        log.info("리뷰 상세");
        log.info("searchKey:{}", searchKey);
        log.info("searchWord:{}", searchWord);
        log.info("review searchList cpage:{}, pageBlock:{}", cpage,pageBlock);
        List<ReviewVO> list= service.searchListPageBlock(searchKey,searchWord,cpage,pageBlock);
        model.addAttribute("list", list);
        log.info("list:{}", list);


        int searchTotalRows= service.getsearchTotalRow(searchKey, searchWord);
        log.info("searchTotalRows:{}", searchTotalRows);

        int totalPageCount =0;
        if (searchTotalRows/ pageBlock ==0){
            totalPageCount =1;
        } else if (searchTotalRows % pageBlock ==0) {
            totalPageCount = searchTotalRows / pageBlock ;
        }else{
            totalPageCount = searchTotalRows / pageBlock +1;
        }
        log.info("totalPageCount:{}", totalPageCount);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("searchKey", searchKey);
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("searchTotalRows", searchTotalRows);

        return "review/list";
    }
//
    //리뷰 입력
    @GetMapping("/review/insert")
    public String insert(@RequestParam(defaultValue = "work_num")int work_num,
                         @RequestParam(defaultValue = "categories")String categories,
                         Model model) {
        log.info("리뷰 입력");
        log.info("work_num:{} ", work_num);
        log.info("categories:{} ", categories);

        String work_title = service.getWorkdata(work_num);
        log.info("work_title:{}", work_title);

        model.addAttribute("work_num", work_num);
        model.addAttribute("categories", categories);
        model.addAttribute("work_title", work_title);
        return "review/insert";

    }
    //리뷰 수정
    @GetMapping("/review/update")
    public String update(ReviewVO vo, Model model) {
        log.info("리뷰 수정");
        log.info("vo:{}", vo);
        ReviewVO vo2 = service.selectOne(vo);
        log.info("vo2:{}", vo2);

        String work_title = service.getWorkdata(vo2.getWork_num());
        log.info("work_title:{}", work_title);

        model.addAttribute("work_title", work_title);
        model.addAttribute("vo2", vo2);

        return "review/update";

    }
    @PostMapping("/review/insertOK")
    public String insertOK(ReviewVO vo) {
        log.info("리뷰 입력");
        int result  = service.insertOK(vo);
        log.info("result:{}", result);

        if (result==1){
            return "redirect:/review/list";
        } return "review/insert";

    }

    //리뷰 수정
    @PostMapping("/review/updateOK")
    public String updateOK(ReviewVO vo) {
        log.info("리뷰 수정");
        log.info("vo:{}", vo);
        int result =  service.updateOK(vo);
        log.info("result:{}", result);
        if (result ==1){
            return "redirect:/review/detail?num="+vo.getNum();
        }return "/review/list";
    }

    //리뷰 삭제
    @GetMapping("/review/deleteOK")
    public String deleteOK(ReviewVO vo){
        log.info("vo:{}", vo);
        log.info("리뷰 삭제");
        int result = service.deleteOK(vo);

        log.info("result:{}", result);

        if (result==1){
            return "redirect:/review/list";
        }return "redirect:/review/detail?num="+vo.getNum();
    }

    //신고 기능
    @GetMapping("/review/reportOK")
    public String r_reportOK(ReviewVO vo) {
        log.info("report ..start");

        service.updateReport(vo);
        return "redirect:/review/detail?num="+vo.getNum();
    }

    //좋아요 기능
    @PostMapping("/likeUpOK")
    public ResponseEntity<LikeResponse> likeReview(@RequestParam int num) {
        service.increamentLikes(num); // 좋아요 수 증가
        Integer likeCount = service.getLikeCount(num); // 증가된 좋아요 수 가져오기
        return ResponseEntity.ok(new LikeResponse(likeCount)); // 응답 반환
    }

    public static class LikeResponse {
        private Integer likeCount;

        public LikeResponse(Integer likeCount) {
            this.likeCount = likeCount;
        }

        public Integer getLikeCount() {
            return likeCount;
        }
    }

    //싫어요 기능
    @PostMapping("/dislikeUpOK")
    public  ResponseEntity<HateResponse> hateReview(@RequestParam int num){
        service.increamentDislikes(num);
        int HateCount = service.getHateCount(num);
        return ResponseEntity.ok(new HateResponse(HateCount));
    }
    public static class HateResponse{
        private int HateCount;

        //count 호출
        public HateResponse(int HateCount){
            this.HateCount = HateCount;
        }

        public int getHateCount(){
            return HateCount;
        }

    }


    //감정분석 기능
    @PostMapping("/analyzeOK")
    @ResponseBody
    public String analyzeSentiment(@RequestParam("content") String content) {
        try {
            return sentimentservice.analyzeSentiment(content);
        } catch (Exception e) {
            return "감정 분석 실패: " + e.getMessage();
        }
    }


}
