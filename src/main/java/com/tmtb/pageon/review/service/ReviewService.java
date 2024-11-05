package com.tmtb.pageon.review.service;

import com.tmtb.pageon.book.model.BookVO;
import com.tmtb.pageon.review.mapper.ReviewMapper;
import com.tmtb.pageon.webnovel.model.WebnovelVO;
import com.tmtb.pageon.webtoon.model.WebtoonVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

       return mapper.review_searchListPage(searchKey,"%"+ searchWord +"%",startRow,pageBlock);

    }

    public int getsearchTotalRow(String searchKey, String searchWord) {
        log.info("review getsearchTotalRow");

        return mapper.review_getsearchListPage(searchKey,"%"+searchWord+"%" );
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


    public List<BookVO> getBookRecommendationBycategory(String id, int cpage, int pageBlock) {
        log.info("getBookRecommendation..");
        int startRow = (cpage -1)*pageBlock;
        log.info("startRow:{}", startRow);

        return mapper.getBookRecommendationBycategory(id, pageBlock, startRow);
    }

    public List<WebtoonVO> getWebtoonRecommendationBycategory(String id, int cpage, int pageBlock) {
        log.info("getWebtoonRecommendation..");
        int startRow = (cpage -1)*pageBlock;
        log.info("startRow:{}", startRow);

        return mapper.getWebtoonRecommendationBycategory(id, pageBlock, startRow);
    }

    public List<WebnovelVO> getWebnovelRecommendationBycategory(String id, int cpage, int pageBlock) {
        log.info("getWebnovelRecommendationBycategory..");
        int startRow = (cpage -1)*pageBlock;
        log.info("startRow:{}", startRow);

        return mapper.getWebnovelRecommendationBycategory(id, pageBlock, startRow);
    }

    public int webnovelGetRecommandationTotalRow(String id) {
        log.info("getRecommandationTotalRow..");
        return  mapper.webnovelGetRecommandationTotalRow(id);
    }

    public int webtoonGetRecommandationTotalRow(String id) {
        return mapper.webtoonGetRecommandationTotalRow(id);
    }

    public int bookGetRecommandationTotalRow(String id) {
        return mapper.bookGetRecommandationTotalRow(id);
    }
}