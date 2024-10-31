package com.tmtb.pageon.review.mapper;


import com.tmtb.pageon.review.model.ReviewVO;
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

//    List<ReviewVO> review_searchListPageTitle(int startRow, int pageBlock, String searchWord);
//
//    List<ReviewVO> review_searchListPageContent(int startRow, int pageBlock, String searchWord);
//
//    int getsearchListPageTitle(int startRow, int pageBlock, String searchWord);
//
//    int getsearchListPageContent(int startRow, int pageBlock, String searchWord);


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
