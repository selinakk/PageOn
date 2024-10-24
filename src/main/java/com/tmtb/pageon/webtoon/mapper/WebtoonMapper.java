package com.tmtb.pageon.webtoon.mapper;

import com.tmtb.pageon.board.model.BoardVO;
import com.tmtb.pageon.webtoon.model.WebtoonVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WebtoonMapper {


    //페이징 관련
    List<WebtoonVO> getWebtoonList(@Param("offset") int offset, @Param("pageSize") int pageSize);

    //검색 관련 + 페이징
    List<WebtoonVO> searchWebtoonByTitle(@Param("searchWord") String searchWord,
                                     @Param("offset") int offset, @Param("pageSize") int pageSize);
    List<WebtoonVO> searchWebtoonWriter(@Param("searchWord") String searchWord,
                                       @Param("offset") int offset, @Param("pageSize") int pageSize);

    int getTotalCountByTitle(@Param("searchWord") String searchWord);
    int getTotalCountByContent(@Param("searchWord") String searchWord);

    int getTotalCount();

    WebtoonVO selectOne(WebtoonVO vo);


    //필터링 관련

    List<WebtoonVO> filterByCategories(@Param("categories") List<String> categories, @Param("offset") int offset, @Param("pageSize") int pageSize);

    int getTotalCountByCategories(@Param("categories") List<String> categories);

    void saveWebtoon(WebtoonVO vo);

}


