package com.tmtb.pageon.comment.controller;

import com.tmtb.pageon.comment.service.CommentService;
import com.tmtb.pageon.comment.model.CommentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public List<CommentVO> selectAll(
            @RequestParam String type,
            @RequestParam(required = false) Integer bnum,
            @RequestParam(required = false) Integer fnum,
            @RequestParam(required = false) Integer rnum) {
        log.info("getComments()...");
        return service.selectAll(type, bnum, fnum, rnum);
    }

    @GetMapping("/child/{cnum}")
    public List<CommentVO> selectAllChild(@PathVariable int cnum) {
        log.info("getChildComments()... cnum :" + cnum);
        List<CommentVO> child = service.selectAllChild(cnum);
        log.info("Retrieved child comments: {}", child);
        return service.selectAllChild(cnum);
    }
}
