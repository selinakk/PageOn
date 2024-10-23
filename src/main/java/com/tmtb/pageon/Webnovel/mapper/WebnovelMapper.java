package com.tmtb.pageon.Webnovel.mapper;

import com.tmtb.pageon.Webnovel.model.WebnovelVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface WebnovelMapper {
    /* 데이터베이스에 알라딘 API 작품 정보 추가 */
    void insertWebnovel(WebnovelVO webnovel);

    int checkDuplicateTitle(String title);

    /* Webnovel 기능 */
    List<WebnovelVO> selectAllWebnovels(int startRow, int pageBlock);
    int getTotalRows();

    List<WebnovelVO> selectWebnovelsByCategory(String category, int startRow, int pageBlock);
    int getTotalRowsByCategory(String category);

    List<WebnovelVO> searchWebnovelsInCategory(String category, String searchKey, String searchWord, int startRow, int pageBlock);
    int getSearchTotalRowsInCategory(String category, String searchKey, String searchWord);

    List<WebnovelVO> searchWebnovels(String searchKey, String searchWord, int startRow, int pageBlock);
    int getSearchTotalRows(String searchKey, String searchWord);

    public WebnovelVO selectOne(WebnovelVO vo);

    List<WebnovelVO> selectLimitedWebnovelsByCategory(Map<String, Object> params);
}
