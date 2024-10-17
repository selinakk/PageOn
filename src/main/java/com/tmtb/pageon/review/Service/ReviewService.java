package com.tmtb.pageon.review.Service;

import com.tmtb.pageon.review.mapper.ReviewMapper;
import com.tmtb.pageon.review.model.ReviewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReviewService {

    @Autowired
    ReviewMapper mapper;




    public ReviewVO selectOne(ReviewVO vo) {
        log.info("review selectOne..");
        return mapper.selectOne(vo);
    }

    public int insertOK(ReviewVO vo) {
        log.info("review insertOK..");
        return mapper.insertOK(vo);
    }
    public int updateOK(ReviewVO vo) {
        log.info("review updateOK..");
        return mapper.updateOK(vo);
    }


    public int deleteOK(ReviewVO vo) {
        log.info("review deleteOK..");
        return mapper.updateOK(vo);
    }


    public int getsearchTotalRow(String searchKey, String searchWord) {
        log.info("review searList");
        return mapper.getsearchTotalRow(searchKey,searchWord);
    }

    public List<ReviewVO> selectAllPageBlock(int cpage, int pageBlock) {
        log.info("review selectAllPageBlock");
        return mapper.selectAllPageBlock(cpage, pageBlock);
    }

    public int getTotalRow() {
        log.info("review getTotalRow");
        return mapper.getTotalRow();
    }

    public List<ReviewVO> searchListPageBlock(String searchKey, String searchWord, int cpage, int pageBlock) {
        log.info("review getTotalRow");
        return mapper.searchListPageBlock(searchKey,searchWord,cpage,pageBlock);
    }
}
