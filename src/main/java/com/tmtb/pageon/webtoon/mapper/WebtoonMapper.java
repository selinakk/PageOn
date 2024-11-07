package com.tmtb.pageon.webtoon.mapper;

import com.tmtb.pageon.board.model.BoardVO;
import com.tmtb.pageon.webnovel.model.WebnovelVO;
import com.tmtb.pageon.webtoon.model.WebtoonVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WebtoonMapper {


    //페이징 관련
    List<WebtoonVO> getWebtoonList(@Param("offset") int offset, @Param("pageSize") int pageSize, String sortOrder);

    //검색 관련 + 페이징
    List<WebtoonVO> searchWebtoonByTitle(@Param("searchWord") String searchWord,
                                         @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<WebtoonVO> searchWebtoonByWriter(@Param("searchWord") String searchWord,
                                          @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<WebtoonVO> searchWebtoonByCategories(@Param("searchWord") String searchWord,
                                              @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<WebtoonVO> selectPopularWebtoons(@Param("offset") int offset, @Param("pageSize") int pageSize);

    List<WebtoonVO> getCategories();

    List<WebtoonVO> searchLikeCategories(@Param("likeCategories") List<String> likeCategories, @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<WebtoonVO> searchMultiCategories(@Param("categories") List<String> categories, @Param("offset") int offset, @Param("pageSize") int pageSize);

    int getTotalCountByMultiCategories(List<String> categories);

    int getTotalCountByLikeCategories(List<String> likeCategories);

    int getTotalCountByTitle(@Param("searchWord") String searchWord);

    int getTotalCountByWriter(@Param("searchWord") String searchWord);

    int getTotalCountByCategories(@Param("searchWord") String searchWord);


    int getTotalCountByPopular();

    int getTotalCount();

    WebtoonVO selectOne(WebtoonVO vo);


    //API - DB 관련
    WebtoonVO findByTitle(String title);

    void updateWebtoon(WebtoonVO vo);

    void saveWebtoon(WebtoonVO vo);

    void updateWebtoonUpdateDays(WebtoonVO vo);


    List<WebtoonVO> getWebtoonRecommendationBycategory(@Param("id") String id,
                                                       @Param("offset") int offset, @Param("pageSize") int pageSize);

    int webtoonGetRecommandationTotalCount(String id);

}


