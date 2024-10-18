package com.tmtb.pageon.comment.controller;

import com.tmtb.pageon.comment.service.CommentService;
import com.tmtb.pageon.comment.model.CommentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService service;

    @PostMapping
    public void insertOK(@RequestBody CommentVO vo) {
        if (vo.getCnum() != 0) { // 대댓글인 경우
            service.insertOKChild(vo); // 대댓글 추가 메서드
            log.info("insertOKChild()... Parent comment ID: " + vo.getCnum());
        } else { // 댓글인 경우
            service.insertOK(vo); // 일반 댓글 추가 메서드
            log.info("insertOK()...");
        }
    }

    @PutMapping("/{num}")
    public void updateOK(@PathVariable int num, @RequestBody CommentVO vo) {
        vo.setNum(num);
        service.updateOK(vo);
        log.info("updateOK()...");
    }

    @DeleteMapping("/{num}")
    public void deleteOK(@PathVariable int num) {
        service.deleteOK(num);
        log.info("deleteOK()...");
    }

    @GetMapping("/comments") // 비동기 요청을 위한 URL
    @ResponseBody // JSON 형식으로 응답
    public Map<String, Object> selectAll(Model model,
                                         @RequestParam String type,
                                         @RequestParam(required = false) Integer bnum,
                                         @RequestParam(required = false) Integer fnum,
                                         @RequestParam(required = false) Integer rnum,
                                         @RequestParam(defaultValue = "1") int cpage,
                                         @RequestParam(defaultValue = "20") int pageBlock) {
        log.info("selectAll()... type: {}, bnum: {}, fnum: {}, rnum: {}, cpage: {}, pageBlock: {}",
                type, bnum, fnum, rnum, cpage, pageBlock);

        // 댓글 리스트 조회
        List<CommentVO> comments = service.selectAllPageBlock(type, bnum, fnum, rnum, cpage, pageBlock);
        log.info("Retrieved comments size: {}", comments.size());

        // 전체 댓글 수 조회
        int totalRows = service.getTotalRows(type, bnum, fnum, rnum);
        log.info("Total comments: {}", totalRows);

        // 총 페이지 수 계산
        int totalPageCount = (int) Math.ceil((double) totalRows / pageBlock);
        log.info("Total pages: {}", totalPageCount);

        // 응답 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("comments", comments);
        response.put("totalRows", totalRows); // totalRows 추가
        response.put("totalPageCount", totalPageCount);
        response.put("cpage", cpage);
        response.put("pageBlock", pageBlock);

        return response; // JSON 형식으로 반환
    }

    @GetMapping("/child/{cnum}")
    public ResponseEntity<Map<String, Object>> selectAllChild(
            @PathVariable int cnum,
            @RequestParam(defaultValue = "1") int cpage,
            @RequestParam(defaultValue = "20") int pageBlock) {

        log.info("getChildComments()... cnum: {}, cpage: {}, pageBlock: {}", cnum, cpage, pageBlock);

        // 댓글 리스트 조회
        List<CommentVO> childComments = service.selectAllChildPageBlock(cnum, cpage, pageBlock);
        log.info("Retrieved child comments size: {}", childComments.size());

        // 전체 댓글 수 조회
        int totalRows = service.getTotalChildRows(cnum);
        log.info("Total child comments: {}", totalRows);

        // 총 페이지 수 계산
        int totalPageCount = (int) Math.ceil((double) totalRows / pageBlock);
        log.info("Total child pages: {}", totalPageCount);

        // 결과를 Map에 담아 ResponseEntity로 반환
        Map<String, Object> response = new HashMap<>();
        response.put("childComments", childComments);
        response.put("totalPageCount", totalPageCount);
        response.put("currentPage", cpage);

        return ResponseEntity.ok(response);
    }


    @PutMapping("/report/{num}")
    public void reportOK(@PathVariable int num) {
        service.reportOK(num);
        log.info("reportOK()... Comment ID: " + num);
    }
}
