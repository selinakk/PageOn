package com.tmtb.pageon.Book.mapper;

import com.tmtb.pageon.Book.model.BookVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {

    /* 데이터베이스에 알라딘 API 작품 정보 추가 */
    void insertBook(BookVO book);

    int checkDuplicateTitle(String title);

    /* Book 기능 */
    List<BookVO> selectAllBooks(int startRow, int pageBlock);
    int getTotalRows();

    List<BookVO> selectBooksByCategory(String category, int startRow, int pageBlock);
    int getTotalRowsByCategory(String category);

    List<BookVO> searchBooksInCategory(String category, String searchKey, String searchWord, int startRow, int pageBlock);
    int getSearchTotalRowsInCategory(String category, String searchKey, String searchWord);

    List<BookVO> searchBooks(String searchKey, String searchWord, int startRow, int pageBlock);
    int getSearchTotalRows(String searchKey, String searchWord);

    public BookVO selectOne(BookVO vo);
}

