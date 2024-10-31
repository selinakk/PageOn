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

    public List<ReviewVO> review_searchListPageBlockTitle(String searchWord, int startRow, int pageBlock);

    public List<ReviewVO> review_searchListPageWork_id(String searchWord, int startRow, int pageBlock);


    public int review_getsearchTotalRowTitle(String searchWord);

    public int review_getsearchTotalRowWork_id(String searchWord);

    int review_updateReport(ReviewVO vo);

    int review_increamentLikes(int num);

    int review_increamentDislikes(int num);

    int getLikeCount(int num);

    int getHateCount(int num);

    List<ReviewVO> reviewfindByUserId(String userId);

    List<ReviewVO> findByUserId(String userId);


//   Map<String, Object> getCategoryByWorkId(@Param("workNum")int workNum);
//
//    //동일한 카테고리인 book, webtoon, webnovel 추천
//    List<BookVO> getBookByCategory(@Param("caregories") String caregories,
//                             @Param("workNum") int workNum);
//
//    List<WebtoonVO> getWebtoonByCategory(@Param("categories") String caregories,
//                                               @Param("workNum") int workNum);
//
//    List<WebnovelVO> getWebnovelByCategry(@Param("categories") String caregories,
//                                          @Param("workNum") int workNum);


    //작성한 리뷰의 작품 카테고리 조회
//    List<ReviewVO> getReviewByCategories(String categories);
//
//    BookVO getBookByNum(int workNum);
//
//    List<BookVO> getBookByCategory(String category);
//
//    WebtoonVO getWebttonByNum(int workNum);
//
//    List<WebtoonVO> getWebtoonByCategory(String category);
//
//    WebnovelVO WebnovelByNum(int workNum);
//
//    List<WebnovelVO> getWebnovelByCategory(String category);
//
//    ReviewVO getReviewByUserId(String userId);
//

    
}
