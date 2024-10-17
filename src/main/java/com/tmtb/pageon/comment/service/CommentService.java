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
        log.info("addComment()...");
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

    public List<CommentVO> selectAll(String type, Integer bnum, Integer fnum, Integer rnum) {
        log.info("selectAll()...");
        return mapper.selectAll(type, bnum, fnum, rnum);
    }

    public List<CommentVO> selectAllChild(int cnum) {
        log.info("getChildComments()...");
        List<CommentVO> child = mapper.selectAllChild(cnum);
        log.info("Retrieved child comments: {}", child);
        return mapper.selectAllChild(cnum);
    }
}

