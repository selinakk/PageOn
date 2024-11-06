package com.tmtb.pageon.webnovel.service;

import com.tmtb.pageon.webnovel.model.WebnovelVO;
import com.tmtb.pageon.webnovel.mapper.WebnovelMapper;
import com.tmtb.pageon.webtoon.model.WebtoonVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class WebnovelService {

    @Autowired
    private WebnovelMapper mapper;

    public List<WebnovelVO> selectAllWebnovels(int cpage, int pageBlock, String sortOrder) {
        int startRow = (cpage - 1) * pageBlock;
        return mapper.selectAllWebnovels(startRow, pageBlock, sortOrder);
    }

    public int getTotalRows() {
        return mapper.getTotalRows();
    }

    public List<WebnovelVO> searchWebnovels(String searchKey, String searchWord, int cpage, int pageBlock, String sortOrder) {
        int startRow = (cpage - 1) * pageBlock;

        // searchKey가 유효하지 않으면 기본값을 title로 설정
        if (!"title".equals(searchKey) && !"writer".equals(searchKey)) {
            searchKey = "title";
        }

        return mapper.searchWebnovels(searchKey, "%" + searchWord + "%", startRow, pageBlock, sortOrder);
    }

    public int getSearchTotalRows(String searchKey, String searchWord) {
        // searchKey가 유효하지 않으면 기본값을 title로 설정
        if (!"title".equals(searchKey) && !"writer".equals(searchKey)) {
            searchKey = "title";
        }

        return mapper.getSearchTotalRows(searchKey, "%" + searchWord + "%");
    }

    public List<WebnovelVO> selectWebnovelsByCategories(List<String> categories, int cpage, int pageBlock, String sortOrder) {
        int startRow = (cpage - 1) * pageBlock;
        return mapper.selectWebnovelsByCategories(categories, startRow, pageBlock, sortOrder);
    }

    public int getTotalRowsByCategories(List<String> categories) {
        return mapper.getTotalRowsByCategories(categories);
    }

    public List<WebnovelVO> searchWebnovelsInCategories(List<String> categories, String searchKey, String searchWord, int cpage, int pageBlock, String sortOrder) {
        int startRow = (cpage - 1) * pageBlock;
        if (!"title".equals(searchKey) && !"writer".equals(searchKey)) {
            searchKey = "title";
        }
        return mapper.searchWebnovelsInCategories(categories, searchKey, "%" + searchWord + "%", startRow, pageBlock, sortOrder);
    }

    public int getSearchTotalRowsInCategories(List<String> categories, String searchKey, String searchWord) {
        if (!"title".equals(searchKey) && !"writer".equals(searchKey)) {
            searchKey = "title";
        }
        return mapper.getSearchTotalRowsInCategories(categories, searchKey, "%" + searchWord + "%");
    }

    public WebnovelVO selectOne(WebnovelVO vo) {
        return mapper.selectOne(vo);
    }

    // 카테고리로 5개의 유사한 책을 조회하는 메서드
    public List<WebnovelVO> getLimitedWebnovelsByCategory(String category, int limit, int item_id) {
        Map<String, Object> params = new HashMap<>();
        params.put("category", category);
        params.put("limit", limit);
        params.put("item_id", item_id);
        return mapper.selectLimitedWebnovelsByCategory(params);
    }

    public List<WebnovelVO> selectPopularWebnovels(int cpage, int pageBlock) {
        int startRow = (cpage - 1) * pageBlock;
        return mapper.selectAllWebnovels(startRow, pageBlock, "popular");
    }

    // added_bs 추가 테스트를 위해 넣어두었음 추후 서재쪽 패키지 확인하고 수정 예정
    public void updateAddedBs(int item_id) {
        mapper.updateAddedBs(item_id);
    }


    public List<WebnovelVO> getWebnovelRecommendationBycategory(String id, int cpage, int pageBlock) {
        log.info("getWebnovelRecommendationBycategory..");
        int startRow = (cpage -1)*pageBlock;
        log.info("startRow:{}", startRow);

        return mapper.getWebnovelRecommendationBycategory(id, pageBlock, startRow);
    }

    public int webnovelGetRecommandationTotalRow(String id) {
        return  mapper.webnovelGetRecommandationTotalRow(id);
    }




}
