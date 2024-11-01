package com.tmtb.pageon.review.mapper;


import com.tmtb.pageon.book.model.BookVO;
import com.tmtb.pageon.review.model.ReviewVO;
import com.tmtb.pageon.webnovel.model.WebnovelVO;
import com.tmtb.pageon.webtoon.model.WebtoonVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Mapper
public interface ReviewMapper {

    public ReviewVO review_selectOne(ReviewVO vo);

    public int review_updateOK(ReviewVO vo);

    int review_deleteOK(ReviewVO vo);


    public int review_insertOK(ReviewVO vo);

    public int review_getTotalRow();


    public List<ReviewVO> review_selectAllPageBlock(@Param("startRow") int startRow,
                                             @Param("pageBlock") int pageBlock,
                                             @Param("sortType") String sortType);

    List<ReviewVO> review_searchListPage(int startRow, int pageBlock, String searchWord, String searchKey);

    int review_getSearchTotalRows( String searchKey, String searchWord, int startRow, int pageBlock);


    int review_updateReport(ReviewVO vo);

    int review_increamentLikes(int num);

    int review_increamentDislikes(int num);

    int getLikeCount(int num);

    int getHateCount(int num);

    List<ReviewVO> reviewfindByUserId(String userId);

    List<ReviewVO> findByUserId(String userId);

    List<Object> getReviewRecommended(String userId);


    List<BookVO> getBookRecommendation(String id, int startRow, int pageBlock);

    List<WebtoonVO> getWebtoonRecommendation(String id, int startRow, int pageBlock);

    List<WebnovelVO> getWebnovelRecommendation(String id, int startRow, int pageBlock);
}
