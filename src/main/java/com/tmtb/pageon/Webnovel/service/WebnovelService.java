package com.tmtb.pageon.Webnovel.service;

import com.tmtb.pageon.Book.model.BookVO;
import com.tmtb.pageon.Webnovel.model.WebnovelVO;
import com.tmtb.pageon.Webnovel.mapper.WebnovelMapper;
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

    public List<WebnovelVO> selectWebnovelsByCategory(String category, int cpage, int pageBlock, String sortOrder) {
        int startRow = (cpage - 1) * pageBlock;
        return mapper.selectWebnovelsByCategory(category, startRow, pageBlock, sortOrder);
    }

    public int getTotalRowsByCategory(String category) {
        return mapper.getTotalRowsByCategory(category);
    }

    public List<WebnovelVO> searchWebnovelsInCategory(String category, String searchKey, String searchWord, int cpage, int pageBlock, String sortOrder) {
        int startRow = (cpage - 1) * pageBlock;

        // searchKey가 유효하지 않으면 기본값을 title로 설정
        if (!"title".equals(searchKey) && !"writer".equals(searchKey)) {
            searchKey = "title";
        }

        return mapper.searchWebnovelsInCategory(category, searchKey, "%" + searchWord + "%", startRow, pageBlock, sortOrder);
    }

    public int getSearchTotalRowsInCategory(String category, String searchKey, String searchWord) {
        // searchKey가 유효하지 않으면 기본값을 title로 설정
        if (!"title".equals(searchKey) && !"writer".equals(searchKey)) {
            searchKey = "title";
        }

        return mapper.getSearchTotalRowsInCategory(category, searchKey, "%" + searchWord + "%");
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

}
