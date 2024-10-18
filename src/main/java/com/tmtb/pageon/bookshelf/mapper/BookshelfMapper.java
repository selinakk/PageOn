package com.tmtb.pageon.bookshelf.mapper;

import com.tmtb.pageon.bookshelf.model.BookshelfVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookshelfMapper {
    List<BookshelfVO> getList(@Param("offset") int offset,
                              @Param("size") int size,
                              @Param("sortField") String sortField,
                              @Param("sortDir") String sortDir);

    int getListCnt();
    void updateSortOK(String sort, int num);

    List<BookshelfVO> getListBySort(@Param("sort") String sort,
                                    @Param("offset") int offset,
                                    @Param("size") int size,
                                    @Param("sortField") String sortField,
                                    @Param("sortDir") String sortDir);

    int getListBySortCnt(@Param("sort") String sort);

    boolean deleteBookshelfOK(int num);
}
