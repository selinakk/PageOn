package com.tmtb.pageon.review.Conroller;


import com.tmtb.pageon.comment.controller.CommentController;
import com.tmtb.pageon.comment.model.CommentVO;
import com.tmtb.pageon.review.Service.ReviewService;
import com.tmtb.pageon.review.Service.SentimentAnalysisService;
import com.tmtb.pageon.review.model.ReviewVO;
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
                           @RequestParam(defaultValue ="8" )int pageBlock,
                           @RequestParam(defaultValue = "num")String sortType,
                           @RequestParam(defaultValue = "desc")String sort
                           //@RequestParam String userId
    ) {
        log.info("리뷰 목록");
       //List<ReviewVO> list = service.selectAll(cpage);
        List<ReviewVO> list = service.selectAllPageBlock(cpage, pageBlock, sortType, sort );
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
        log.info("sort:{}", sort);

        log.info("pageBlock:{}", pageBlock);
        model.addAttribute("cpage", cpage);



        return "review/list";
    }

    //리뷰 상세
    @GetMapping("/review/detail")
    public String selectOne(Model model, ReviewVO vo,
                            @RequestParam(defaultValue = "1")int cpage,
                            @RequestParam(defaultValue = "20")int pageBlock){
        log.info("리뷰 상세");
        ReviewVO vo2 = service.selectOne(vo);
        model.addAttribute("vo2", vo2);

        log.info("testPage()... cpage: {}, pageBlock: {}", cpage, pageBlock);

        //  CommentController의 selectAll() 메서드를 호출하여 댓글 데이터를 가져옵니다.
        Map<String, Object> commentsData = controller.selectAll("forum", null, 1, null, cpage, pageBlock); // type과 부모글의


        // 댓글 목록 가져오기
        List<CommentVO> comments = (List<CommentVO>) commentsData.get("comments");

        // 전체 댓글 수 가져오기
        int totalRows = (int) commentsData.get("totalRows");


        model.addAttribute("cpage", cpage);
        model.addAttribute("pageBlock", pageBlock);
        model.addAttribute("comments", comments);
        model.addAttribute("cpage", cpage);







        log.info("vo2:{}", vo2);
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
        log.info("cpage:{}", cpage);
        log.info("pageBlock:{}", pageBlock);
        List<ReviewVO> list= service.searchListPageBlock(searchKey,searchWord,cpage,pageBlock);
        model.addAttribute("list:", list);
        log.info("list:{}", list);


        int total_rows= service.getsearchTotalRow(searchKey, searchWord);

        int totalPageCount =0;
        if (total_rows/ pageBlock ==0){
            totalPageCount =1;
        } else if (total_rows % pageBlock ==0) {
            totalPageCount = total_rows / pageBlock ;
        }else{
            totalPageCount = total_rows / pageBlock +1;
        }
        log.info("totalPageCount:{}", totalPageCount);

        model.addAttribute("totalPageCount", totalPageCount);

        return "review/list";
    }
//
    //리뷰 입력
    @GetMapping("/review/insert")
    public String insert() {
        log.info("리뷰 입력");
        return "review/insert";

    }
    //리뷰 수정
    @GetMapping("/review/update")
    public String update(ReviewVO vo, Model model) {
        log.info("리뷰 수정");
        log.info("vo:{}", vo);
        ReviewVO vo2 = service.selectOne(vo);
        log.info("vo2:{}", vo2);

        model.addAttribute("vo2", vo2);

        return "review/update";

    }
    //리뷰 삭제
    @GetMapping("/review/delete")
    public String delete() {
        log.info("리뷰 삭제");
        return "review/delete";

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
        }return "redirect:/review/listOne?num="+vo.getNum();
    }

    //신고 기능
    @PostMapping("/r_reportOK")
    @ResponseBody
    public ResponseEntity<String> r_reportOK(ReviewVO vo) {
        log.info("report ..start");

        service.updateReport(vo);
        return new ResponseEntity<>("신고 완료", HttpStatus.OK) ;
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

    //리뷰 작성 시 해당되는 작품 카테고리 추천
//    @PostMapping("/recommendation")
//    public ResponseEntity<?> recommentdation(@RequestParam(defaultValue = "titlw_id")int title_id{
//
//        List<Work> recommendation = service.getrecommendation(title_id);
//
//        //추천리스츠 작품 반
//        return  ResponseEntity.ok(recommendation);
//
//
//
//    }


}
