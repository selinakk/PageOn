package com.tmtb.pageon.review.Conroller;


import com.tmtb.pageon.review.Service.ReviewService;
import com.tmtb.pageon.review.model.ReviewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
public class ReviewContoller {

    @Autowired
    ReviewService service;

    @GetMapping("/review_list")
    public String getUsers(Model model, @RequestParam(defaultValue = "0")int cpage,
                           @RequestParam(defaultValue ="10" )int pageBlock) {
        log.info("리뷰 목록");
        //List<ReviewVO> list = service.selectAll(cpage);
        List<ReviewVO> list = service.selectAllPageBlock(cpage, pageBlock);
        model.addAttribute("list", list);
        log.info("list.size:{}", list.size());

        int total_Row =service.getTotalRow();
        int totalPageCount =0;
        if (total_Row / pageBlock ==0){
            totalPageCount =1;
        } else if (total_Row % pageBlock ==0) {
            totalPageCount = total_Row / pageBlock;
        } else {
            totalPageCount = total_Row / pageBlock +1;
        }

        model.addAttribute("totalPageCount:", totalPageCount);
        log.info("totalPageCount:{}", totalPageCount);

        return "review/list";
    }@GetMapping("/review_listOne")
    public String selectOne(Model model, ReviewVO vo){
        log.info("리뷰 상세");
        ReviewVO vo2 = service.selectOne(vo);
        model.addAttribute("vo2:", vo2);
        log.info("vo2:{}", vo2);
        return "review/listOne";
    }
    @GetMapping("/review_searchList")
    public String searchList(Model model, ReviewVO vo, @RequestParam(defaultValue = "title")String searchKey,
                             @RequestParam(defaultValue = "book")String searchWord,
                             @RequestParam(defaultValue = "1")int cpage,
                             @RequestParam(defaultValue = "10")int pageBlock){
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

    @GetMapping("/review_insert")
    public String insert() {
        log.info("리뷰 입력");
        return "review/insert";

    } @GetMapping("/review_update")
    public String update(ReviewVO vo, Model model) {
        log.info("리뷰 수정");
        ReviewVO vo2 = service.selectOne(vo);
        model.addAttribute("vo2: ", vo2);

        return "review/update";

    } @GetMapping("/review/delete")
    public String delete() {
        log.info("리뷰 삭제");
        return "review/delete";

    }
    @PostMapping("/review_insertOK")
    public String inserOK(ReviewVO vo) {
        log.info("리뷰 입력");
        int result  = service.insertOK(vo);
        if (result==1){
            return "redirect:/review_list";
        } return "review/insert";

    }
    @PostMapping("/review/updateOK")
    public String updateOK(Model model, ReviewVO vo) {
        log.info("리뷰 수정");
        int result =  service.updateOK(vo);
        if (result ==1){
            return "redirect/:review/selectOne";
        }return "redirect/:review/update";
    }@PostMapping("/review/deleteOK")
    public String deleteOK(ReviewVO vo){
        log.info("리뷰 삭제");
        int result = service.deleteOK(vo);

        if (result==1){
            return "redirect/:selectAll";
        }return "redirect/:delete0";
    }

}
