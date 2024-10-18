package com.tmtb.pageon.bookshelf.service;


import com.tmtb.pageon.bookshelf.mapper.BookshelfMapper;
import com.tmtb.pageon.bookshelf.model.BookshelfVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BookshelfService {
    @Autowired
    BookshelfMapper mapper;

    public List<BookshelfVO> getList(int page, int size, String sortField, String sortDir) {
        int offset = (page - 1) * size;
        return mapper.getList(offset, size, sortField, sortDir);
    }

    public int getListCnt() {return mapper.getListCnt();}

    public void updateSortOK(String sort, int num) {mapper.updateSortOK(sort, num);}

    public List<BookshelfVO> getListBySort(String sort, int page, int size, String sortField, String sortDir) {
        int offset = (page - 1) * size;
        return mapper.getListBySort(sort,offset,size,sortField,sortDir);
    }

    public int getListBySortCnt(String sort) {return mapper.getListBySortCnt(sort);}

    public boolean deleteBookshelfOK(int num) {return mapper.deleteBookshelfOK(num);}
}
