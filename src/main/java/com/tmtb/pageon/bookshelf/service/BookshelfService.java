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

    public List<BookshelfVO> getList(int page, int size, String sortField, String sortDir, String userId) {
        int offset = (page - 1) * size;
        return mapper.getList(offset, size, sortField, sortDir, userId);
    }
    public List<BookshelfVO> getListBySort(String sort, int page, int size, String sortField, String sortDir, String userId) {
        int offset = (page - 1) * size;
        return mapper.getListBySort(sort,offset,size,sortField,sortDir,userId);
    }
    public int getListCnt(String userId) {return mapper.getListCnt(userId);}
    public int getListBySortCnt(String sort, String userId) {return mapper.getListBySortCnt(sort, userId);}
    public String getUserName(String userId) {return mapper.getUserName(userId);}

    public boolean insertBookshelfOK(String userId, String sort, int workNum) {return mapper.insertBookshelfOK(userId,sort,workNum);}
    public void updateSortOK(String sort, int num) {mapper.updateSortOK(sort, num);}
    public boolean deleteBookshelfOK(int num) {return mapper.deleteBookshelfOK(num);}
}
