package com.tmtb.pageon.review.mapper;

import com.tmtb.pageon.review.model.ReviewVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewMapper {

    public ReviewVO review_selectOne(ReviewVO vo);

    public int review_updateOK(ReviewVO vo);

    int review_deleteOK(ReviewVO vo);


    public int review_insertOK(ReviewVO vo);

    public int review_getTotalRow();


    public List<ReviewVO> review_selectAllPageBlock(@Param("startRow") int startRow,
                                             @Param("pageBlock") int pageBlock,
                                             @Param("sortType") String sortType,
                                             @Param("sort") String sort);

    public List<ReviewVO> review_searchListPageBlockTitle(String searchWord, int startRow, int pageBlock);

    public List<ReviewVO> review_searchListPageWork_id(String searchWord, int startRow, int pageBlock);


    public int review_getsearchTotalRowTitle(String searchWord);

    public int review_getsearchTotalRowWork_id(String searchWord);

    int review_updateReport(ReviewVO vo);

    int review_increamentLikes(int num);

    int review_increamentDislikes(int num);

    int getLikeCount(int num);

    int getHateCount(int num);



}
