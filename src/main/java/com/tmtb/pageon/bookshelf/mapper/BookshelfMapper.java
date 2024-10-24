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
                              @Param("sortDir") String sortDir,
                              @Param("user_id") String userId);

    List<BookshelfVO> getListBySort(@Param("sort") String sort,
                                    @Param("offset") int offset,
                                    @Param("size") int size,
                                    @Param("sortField") String sortField,
                                    @Param("sortDir") String sortDir,
                                    @Param("userId") String userId);

    int getListCnt(@Param("userId") String userId);
    int getListBySortCnt(@Param("sort") String sort, @Param("userId") String userId);
    String getUserName(@Param("userId") String userId);

    boolean insertBookshelfOK(@Param("userId") String userId, @Param("sort") String sort, @Param("workNum") int workNum);
    void updateSortOK(@Param("sort") String sort, @Param("num") int num);
    boolean deleteBookshelfOK(@Param("num") int num);
}
