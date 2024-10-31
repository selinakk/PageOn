package com.tmtb.pageon.notice.service;

import com.tmtb.pageon.notice.mapper.NoticeMapper;
import com.tmtb.pageon.notice.model.NoticeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class NoticeService {

    @Autowired
    NoticeMapper mapper;


    public List<NoticeVO> selectAll() {
        return mapper.selectAll();
    }



    public List<NoticeVO> selectAllSortedPageBlock(int cpage, int pageBlock, String sort) {
        int startRow = (cpage - 1) * pageBlock;
        return mapper.selectAllSortedPageBlock(startRow, pageBlock, sort);
    }




    public int getTotalRows() {
        return mapper.getTotalRows();
    }



    public List<NoticeVO> searchListPageBlock(String searchKey, String searchWord, int cpage, int pageBlock) {
        int startRow = (cpage - 1) * pageBlock ;

        if (searchKey.equals("title")) {
            return mapper.searchListPageBlockTitle("%" + searchWord + "%", startRow, pageBlock);
        } else {
            return mapper.searchListPageBlockContent("%" + searchWord + "%", startRow, pageBlock);
        }
    }



    public int getSearchTotalRows(String searchKey, String searchWord) {
        if (searchKey.equals("title")) {
            return mapper.getSearchTotalRowsTitle("%" + searchWord + "%");
        } else {
            return mapper.getSearchTotalRowsContent("%" + searchWord + "%");
        }
    }



    public NoticeVO selectOne(NoticeVO vo) {

        mapper.hitcountUpdate(vo);

        return mapper.selectOne(vo);
    }



    public int insertOK(NoticeVO vo) {
        return mapper.insertOK(vo);
    }



    public int updateOK(NoticeVO vo) {
        return mapper.updateOK(vo);
    }



    public int deleteOK(NoticeVO vo) {
        return mapper.deleteOK(vo);
    }
}