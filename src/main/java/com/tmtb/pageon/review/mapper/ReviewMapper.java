package com.tmtb.pageon.review.mapper;

import com.tmtb.pageon.review.model.ReviewVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
    // public List<ReviewVO> selectAll();

    public ReviewVO selectOne(ReviewVO vo);

    public int updateOK(ReviewVO vo);

    public int insertOK(ReviewVO vo);


    public int getsearchTotalRow(String searchKey, String searchWord);

    public List<ReviewVO> selectAllPageBlock(int cpage, int pageBlock);

    public int getTotalRow();

    public List<ReviewVO> searchListPageBlock(String searchKey, String searchWord, int cpage, int pageBlock);
}
