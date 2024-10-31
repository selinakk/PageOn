package com.tmtb.pageon.review.service;

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
        return mapper.review_selectOne(vo);
    }

    public int insertOK(ReviewVO vo) {
        log.info("review insertOK..");
        return mapper.review_insertOK(vo);
    }

    public int updateOK(ReviewVO vo) {
        log.info("review updateOK..");
        return mapper.review_updateOK(vo);
    }


    public int deleteOK(ReviewVO vo) {
        log.info("review deleteOK..");
        return mapper.review_deleteOK(vo);
    }

    public int getsearchTotalRow(String searchKey, String searchWord) {
        log.info("review getsearchTotalRow");

        if (searchKey.equals("title")) {
            return mapper.review_getsearchTotalRowTitle("%" + searchWord + "%");
        } else {
            return mapper.review_getsearchTotalRowWork_id("%" + searchWord + "%");
        }
    }

    public List<ReviewVO> selectAllPageBlock(int cpage, int pageBlock, String sortType, String sort) {
        log.info("review selectAllPageBlock");

        int startRow = (cpage - 1) * pageBlock;
        log.info("startRow:{}", startRow);
        log.info("pageBlock:{}", pageBlock);
        log.info("sortType:{}", sortType);
        log.info("sort:{}", sort);


        return mapper.review_selectAllPageBlock(startRow, pageBlock, sortType, sort);
    }

    public int getTotalRow() {
        log.info("review getTotalRow");
        return mapper.review_getTotalRow();
    }
    //
    public List<ReviewVO> searchListPageBlock(String searchKey, String searchWord, int cpage, int pageBlock) {
        log.info("review getTotalRow");

        int startRow = (cpage - 1) * pageBlock;
        log.info("startRow:{}", startRow);
        log.info("pageBlock:{}", pageBlock);

        if (searchKey.equals("title")) {
            return mapper.review_searchListPageBlockTitle("%" + searchWord + "%", startRow, pageBlock);
        } else {
            return mapper.review_searchListPageWork_id("%" + searchWord + "%", startRow, pageBlock);
        }

    }




    public int updateReport(ReviewVO vo) {
        log.info("updateReport..");
        return mapper.review_updateReport(vo);
    }

    public int increamentLikes(int num) {
        log.info("increaseLike ...");
        return mapper.review_increamentLikes(num);
    }
    //
    public int increamentDislikes(int num) {
        log.info("increamentDislikes ,,");
        return mapper.review_increamentDislikes(num);
    }

    public int getLikeCount(int num) {
        return  mapper.getLikeCount(num);
    }

    public int getHateCount(int num) {
        return mapper.getHateCount(num);
    }

    public void getUserProfile(String userId) {
    }


//    public List<Work> getrecommendation(int titleId) {
//        return mapper.writeFindWorkByCategory(titleId);
//    }
}
