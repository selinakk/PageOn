package com.tmtb.pageon.book.mapper;

import com.tmtb.pageon.book.model.BookVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BookMapper {

    /* 데이터베이스에 알라딘 API 작품 정보 추가 */
    void insertBook(BookVO book);

    int checkDuplicateTitle(String title);

    /* Book 기능 */
    List<BookVO> selectAllBooks(int startRow, int pageBlock, String sortOrder);
    int getTotalRows();

    List<BookVO> selectBooksByCategories(List<String> categories, int startRow, int pageBlock, String sortOrder);
    int getTotalRowsByCategories(List<String> categories);

    List<BookVO> searchBooksInCategories(List<String> categories, String searchKey, String searchWord, int startRow, int pageBlock, String sortOrder);
    int getSearchTotalRowsInCategories(List<String> categories, String searchKey, String searchWord);

    List<BookVO> searchBooks(String searchKey, String searchWord, int startRow, int pageBlock, String sortOrder);
    int getSearchTotalRows(String searchKey, String searchWord);

    public BookVO selectOne(BookVO vo);

    List<BookVO> selectLimitedBooksByCategory(Map<String, Object> params);

    // added_bs 추가 테스트를 위해 넣어두었음 추후 서재쪽 패키지 확인하고 수정 예정
    void updateAddedBs(int item_id);

}
