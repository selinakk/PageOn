package com.tmtb.pageon.webnovel.mapper;

import com.tmtb.pageon.webnovel.model.WebnovelVO;
import com.tmtb.pageon.webtoon.model.WebtoonVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface WebnovelMapper {
    /* 데이터베이스에 알라딘 API 작품 정보 추가 */
    void insertWebnovel(WebnovelVO webnovel);

    int checkDuplicateTitle(String title);

    /* Webnovel 기능 */
    List<WebnovelVO> selectAllWebnovels(int startRow, int pageBlock, String sortOrder);
    int getTotalRows();

    List<WebnovelVO> selectWebnovelsByCategories(List<String> categories, int startRow, int pageBlock, String sortOrder);
    int getTotalRowsByCategories(List<String> categories);

    List<WebnovelVO> searchWebnovelsInCategories(List<String> categories, String searchKey, String searchWord, int startRow, int pageBlock, String sortOrder);
    int getSearchTotalRowsInCategories(List<String> categories, String searchKey, String searchWord);

    List<WebnovelVO> searchWebnovels(String searchKey, String searchWord, int startRow, int pageBlock, String sortOrder);
    int getSearchTotalRows(String searchKey, String searchWord);

    public WebnovelVO selectOne(WebnovelVO vo);

    List<WebnovelVO> selectLimitedWebnovelsByCategory(Map<String, Object> params);

    // added_bs 추가 테스트를 위해 넣어두었음 추후 서재쪽 패키지 확인하고 수정 예정
    void updateAddedBs(int item_id);

    List<WebnovelVO> getWebnovelRecommendationBycategory(@Param("id")String id, @Param("pageBlock") int pageBlock, @Param("startRow") int startRow, String sortOrder);

    int webnovelGetRecommandationTotalRow(String id);

}
