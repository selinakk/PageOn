package com.tmtb.pageon.Book.service;

import com.tmtb.pageon.Book.mapper.BookMapper;
import com.tmtb.pageon.Book.model.BookVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BookService {

    @Autowired
    private BookMapper mapper;

    public List<BookVO> selectAllBooks(int cpage, int pageBlock) {
        int startRow = (cpage - 1) * pageBlock;
        return mapper.selectAllBooks(startRow, pageBlock);
    }

    public int getTotalRows() {
        return mapper.getTotalRows();
    }

    public List<BookVO> searchBooks(String searchKey, String searchWord, int cpage, int pageBlock) {
        int startRow = (cpage - 1) * pageBlock;

        // searchKey가 유효하지 않으면 기본값을 title로 설정
        if (!"title".equals(searchKey) && !"writer".equals(searchKey)) {
            searchKey = "title";
        }

        return mapper.searchBooks(searchKey, "%" + searchWord + "%", startRow, pageBlock);
    }

    public int getSearchTotalRows(String searchKey, String searchWord) {
        // searchKey가 유효하지 않으면 기본값을 title로 설정
        if (!"title".equals(searchKey) && !"writer".equals(searchKey)) {
            searchKey = "title";
        }

        return mapper.getSearchTotalRows(searchKey, "%" + searchWord + "%");
    }

    public List<BookVO> selectBooksByCategory(String category, int cpage, int pageBlock) {
        int startRow = (cpage - 1) * pageBlock;
        return mapper.selectBooksByCategory(category, startRow, pageBlock);
    }

    public int getTotalRowsByCategory(String category) {
        return mapper.getTotalRowsByCategory(category);
    }

    public List<BookVO> searchBooksInCategory(String category, String searchKey, String searchWord, int cpage, int pageBlock) {
        int startRow = (cpage - 1) * pageBlock;

        // searchKey가 유효하지 않으면 기본값을 title로 설정
        if (!"title".equals(searchKey) && !"writer".equals(searchKey)) {
            searchKey = "title";
        }

        return mapper.searchBooksInCategory(category, searchKey, "%" + searchWord + "%", startRow, pageBlock);
    }

    public int getSearchTotalRowsInCategory(String category, String searchKey, String searchWord) {
        // searchKey가 유효하지 않으면 기본값을 title로 설정
        if (!"title".equals(searchKey) && !"writer".equals(searchKey)) {
            searchKey = "title";
        }

        return mapper.getSearchTotalRowsInCategory(category, searchKey, "%" + searchWord + "%");
    }

    public BookVO selectOne(BookVO vo) {
        return mapper.selectOne(vo);
    }

    // 카테고리로 5개의 유사한 책을 조회하는 메서드
    public List<BookVO> getLimitedBooksByCategory(String category, int limit, int item_id) {
        Map<String, Object> params = new HashMap<>();
        params.put("category", category);
        params.put("limit", limit);
        params.put("item_id", item_id);
        return mapper.selectLimitedBooksByCategory(params);
    }

}
