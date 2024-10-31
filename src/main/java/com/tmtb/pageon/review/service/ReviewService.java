package com.tmtb.pageon.review.service;


import com.tmtb.pageon.review.mapper.ReviewMapper;
import com.tmtb.pageon.review.model.ReviewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.boot.web.server.Ssl.ClientAuth.map;

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


    public List<ReviewVO> selectAllPageBlock(int cpage, int pageBlock, String sortType) {
        log.info("review selectAllPageBlock");

        int startRow = (cpage - 1) * pageBlock;
        log.info("startRow:{}", startRow);
        log.info("pageBlock:{}", pageBlock);
        log.info("sortType:{}", sortType);


        return mapper.review_selectAllPageBlock(startRow, pageBlock, sortType);
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
        return mapper.review_searchListPage(startRow, pageBlock,"%" + searchWord + "%", searchKey);
//        if(searchKey.equals("title")){
//
//            return mapper.review_searchListPageTitle(startRow, pageBlock,"%" + searchWord + "%");
//        }else{
//            return mapper.review_searchListPageContent(startRow, pageBlock,"%" + searchWord + "%");
//        }


    }
    public int getsearchTotalRow(String searchKey, String searchWord, int pageBlock, int cpage) {
        log.info("review getsearchTotalRow");
        int startRow = (cpage - 1) * pageBlock;

//        if(searchKey.equals("title")){
//
//            return mapper.getsearchListPageTitle(startRow, pageBlock,"%" + searchWord + "%");
//        }else{
//            return mapper.getsearchListPageContent(startRow, pageBlock,"%" + searchWord + "%");
//        }
        return mapper.review_getSearchTotalRows( searchKey,  "%"+searchWord+"%", startRow ,pageBlock);
    }


    public int updateReport(ReviewVO vo) {
        log.info("updateReport..");
        return mapper.review_updateReport(vo);
    }

    public int increamentLikes(int num) {
        log.info("increaseLike ...");
        return mapper.review_increamentLikes(num);
    }

    public int increamentDislikes(int num) {
        log.info("increamentDislikes ,,");
        return mapper.review_increamentDislikes(num);
    }

    public int getLikeCount(int num) {
        return mapper.getLikeCount(num);
    }

    public int getHateCount(int num) {
        return mapper.getHateCount(num);
    }



    public List<ReviewVO> getRecentReview(int cpage, int pageBlock) {
        int start_Row = (cpage -1) * pageBlock;
        return mapper.review_selectAllPageBlock(start_Row,pageBlock,"recent");
    }

    public List<Object> getReviewRecommended(String userId) {

        return mapper.getReviewRecommended(userId);
    }
}