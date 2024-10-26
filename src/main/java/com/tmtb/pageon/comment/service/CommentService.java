package com.tmtb.pageon.comment.service;

import com.tmtb.pageon.comment.model.CommentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tmtb.pageon.comment.mapper.CommentMapper;

import java.util.List;

@Slf4j
@Service
public class CommentService {

    @Autowired
    private CommentMapper mapper;

    public void insertOK(CommentVO vo) {
        log.info("insertOK()...");
        mapper.insertOK(vo);
    }

    public void insertOKChild(CommentVO vo) {
        log.info("insertOKChild()...");
        mapper.insertOK(vo); // 대댓글은 댓글과 같은 매퍼 메서드를 사용
    }


    public void updateOK(CommentVO vo) {
        log.info("updateOK()...");
        mapper.updateOK(vo);
    }

    public void deleteOK(int num) {
        log.info("deleteOK()...");
        mapper.deleteOK(num);
    }

    public List<CommentVO> selectAllPageBlock(String type, Integer bnum, Integer fnum, Integer rnum, int cpage, int pageBlock) {
        int startRow = (cpage - 1) * pageBlock;
        return mapper.selectAll(type, bnum, fnum, rnum, startRow, pageBlock);
    }

    public int getTotalRows(String type, Integer bnum, Integer fnum, Integer rnum) {
        return mapper.getTotalRows(type, bnum, fnum, rnum);
    }

    public List<CommentVO> selectAllChildPageBlock(Integer cnum, int cpage, int pageBlock) {
        int startRow = (cpage - 1) * pageBlock;
        return mapper.selectAllChild(cnum, startRow, pageBlock);
    }

    public int getTotalChildRows(int cnum) {
        return mapper.getTotalChildRows(cnum);
    }

    public void reportOK(int num) {
        // 기존의 신고 상태가 0인 경우에만 1로 업데이트
        if (mapper.checkReport(num) == 0) {
            log.info("reportOK()... Updating report status for comment: " + num);
            mapper.reportOK(num);
        } else {
            log.info("reportOK()... Comment already reported: " + num);
        }
    }
}
