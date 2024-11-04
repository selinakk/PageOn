package com.tmtb.pageon.review.mapper;

import com.tmtb.pageon.book.model.BookVO;
import com.tmtb.pageon.review.model.ReviewVO;
import com.tmtb.pageon.webnovel.model.WebnovelVO;
import com.tmtb.pageon.webtoon.model.WebtoonVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewMapper {

    ReviewVO review_selectOne(ReviewVO vo);

    int review_updateOK(ReviewVO vo);

    int review_deleteOK(ReviewVO vo);


    int review_insertOK(ReviewVO vo);

    int review_getTotalRow();


    List<ReviewVO> review_selectAllPageBlock(@Param("startRow") int startRow,
                                             @Param("pageBlock") int pageBlock,
                                             @Param("sortType") String sortType);

    List<ReviewVO> review_searchListPage(String searchKey, String searchWord, int startRow, int pageBlock);

    int review_getsearchListPage(String searchKey, String searchWord );

    int review_updateReport(ReviewVO vo);

    int review_increamentLikes(int num);

    int review_increamentDislikes(int num);

    int getLikeCount(int num);

    int getHateCount(int num);




    List<BookVO> getBookRecommendation(String id, int pageBlock, int startRow);

    List<WebnovelVO> getWebnovelRecommendation(String id, int pageBlock, int startRow);

    List<WebtoonVO> getWebtoonRecommendation(String id, int pageBlock, int startRow);
}
