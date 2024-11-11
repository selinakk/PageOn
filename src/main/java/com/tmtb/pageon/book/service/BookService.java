package com.tmtb.pageon.book.service;

import com.tmtb.pageon.book.mapper.BookMapper;
import com.tmtb.pageon.book.model.BookVO;
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

    public List<BookVO> selectAllBooks(int cpage, int pageBlock, String sortOrder) {
        int startRow = (cpage - 1) * pageBlock;
        log.info("selectAllBooks called with cpage: {}, pageBlock: {}, sortOrder: {}", cpage, pageBlock, sortOrder);
        List<BookVO> books = mapper.selectAllBooks(startRow, pageBlock, sortOrder);
        log.info("selectAllBooks result: {} books found", books.size());
        return books;
    }

    public int getTotalRows() {
        log.info("getTotalRows called");
        int totalRows = mapper.getTotalRows();
        log.info("Total rows: {}", totalRows);
        return totalRows;
    }

    public List<BookVO> searchBooks(String searchKey, String searchWord, int cpage, int pageBlock, String sortOrder) {
        int startRow = (cpage - 1) * pageBlock;
        log.info("searchBooks called with searchKey: {}, searchWord: {}, cpage: {}, pageBlock: {}, sortOrder: {}", searchKey, searchWord, cpage, pageBlock, sortOrder);

        if (!"title".equals(searchKey) && !"writer".equals(searchKey)) {
            searchKey = "title";
            log.warn("Invalid searchKey, defaulting to 'title'");
        }

        List<BookVO> books = mapper.searchBooks(searchKey, "%" + searchWord + "%", startRow, pageBlock, sortOrder);
        log.info("searchBooks result: {} books found", books.size());
        return books;
    }

    public int getSearchTotalRows(String searchKey, String searchWord) {
        log.info("getSearchTotalRows called with searchKey: {}, searchWord: {}", searchKey, searchWord);

        if (!"title".equals(searchKey) && !"writer".equals(searchKey)) {
            searchKey = "title";
            log.warn("Invalid searchKey, defaulting to 'title'");
        }

        int totalRows = mapper.getSearchTotalRows(searchKey, "%" + searchWord + "%");
        log.info("Search total rows: {}", totalRows);
        return totalRows;
    }

    public List<BookVO> selectBooksByCategories(List<String> categories, int cpage, int pageBlock, String sortOrder) {
        int startRow = (cpage - 1) * pageBlock;
        log.info("selectBooksByCategories called with categories: {}, cpage: {}, pageBlock: {}, sortOrder: {}", categories, cpage, pageBlock, sortOrder);
        List<BookVO> books = mapper.selectBooksByCategories(categories, startRow, pageBlock, sortOrder);
        log.info("selectBooksByCategories result: {} books found", books.size());
        return books;
    }

    public int getTotalRowsByCategories(List<String> categories) {
        log.info("getTotalRowsByCategories called with categories: {}", categories);
        int totalRows = mapper.getTotalRowsByCategories(categories);
        log.info("Total rows by categories: {}", totalRows);
        return totalRows;
    }

    public List<BookVO> searchBooksInCategories(List<String> categories, String searchKey, String searchWord, int cpage, int pageBlock, String sortOrder) {
        int startRow = (cpage - 1) * pageBlock;
        log.info("searchBooksInCategories called with categories: {}, searchKey: {}, searchWord: {}, cpage: {}, pageBlock: {}, sortOrder: {}", categories, searchKey, searchWord, cpage, pageBlock, sortOrder);

        if (!"title".equals(searchKey) && !"writer".equals(searchKey)) {
            searchKey = "title";
            log.warn("Invalid searchKey, defaulting to 'title'");
        }

        List<BookVO> books = mapper.searchBooksInCategories(categories, searchKey, "%" + searchWord + "%", startRow, pageBlock, sortOrder);
        log.info("searchBooksInCategories result: {} books found", books.size());
        return books;
    }

    public int getSearchTotalRowsInCategories(List<String> categories, String searchKey, String searchWord) {
        log.info("getSearchTotalRowsInCategories called with categories: {}, searchKey: {}, searchWord: {}", categories, searchKey, searchWord);

        if (!"title".equals(searchKey) && !"writer".equals(searchKey)) {
            searchKey = "title";
            log.warn("Invalid searchKey, defaulting to 'title'");
        }

        int totalRows = mapper.getSearchTotalRowsInCategories(categories, searchKey, "%" + searchWord + "%");
        log.info("Search total rows in categories: {}", totalRows);
        return totalRows;
    }

    public BookVO selectOne(BookVO vo) {
        log.info("selectOne called with BookVO: {}", vo);
        BookVO book = mapper.selectOne(vo);
        log.info("selectOne result: {}", book);
        return book;
    }

    public List<BookVO> getLimitedBooksByCategory(String category, int limit, int item_id) {
        log.info("getLimitedBooksByCategory called with category: {}, limit: {}, item_id: {}", category, limit, item_id);
        Map<String, Object> params = new HashMap<>();
        params.put("category", category);
        params.put("limit", limit);
        params.put("item_id", item_id);
        List<BookVO> books = mapper.selectLimitedBooksByCategory(params);
        log.info("getLimitedBooksByCategory result: {} books found", books.size());
        return books;
    }

    public List<BookVO> selectPopularBooks(int cpage, int pageBlock) {
        int startRow = (cpage - 1) * pageBlock;
        log.info("selectPopularBooks called with cpage: {}, pageBlock: {}", cpage, pageBlock);
        List<BookVO> books = mapper.selectAllBooks(startRow, pageBlock, "popular");
        log.info("selectPopularBooks result: {} books found", books.size());
        return books;
    }

    public List<BookVO> getBookRecommendationBycategory(String id, int cpage, int pageBlock, String sortOrder) {
        log.info("getBookRecommendation..");
        int startRow = (cpage -1)*pageBlock;
        log.info("startRow:{}", startRow);

        return mapper.getBookRecommendationBycategory(id, pageBlock, startRow, sortOrder);
    }

    public int bookGetRecommandationTotalRow(String id) {
        return mapper.bookGetRecommandationTotalRow(id);
    }

}
