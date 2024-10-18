package com.tmtb.pageon.notice.mapper;

import com.tmtb.pageon.notice.model.NoticeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface NoticeMapper {


    public List<NoticeVO> selectAll();

    public List<NoticeVO> searchListTitle(String searchWord);

    public List<NoticeVO> searchListContent(String searchWord);

    public int insertOK(NoticeVO vo);

    public NoticeVO selectOne(NoticeVO vo);

    public int updateOK(NoticeVO vo);

    public int deleteOK(NoticeVO vo);

    public List<NoticeVO> selectAllPageBlock(int startRow, int pageBlock);

    public  int getTotalRows();

    public List<NoticeVO> selectAllNew();

    public List<NoticeVO> selectAllNewPageBlock(int startRow, int pageBlock);

    public List<NoticeVO> searchListPageBlockTitle(String searchWord, int startRow, int pageBlock);
    public List<NoticeVO> searchListPageBlockContent(String searchWord, int startRow, int pageBlock);

    public int getSearchTotalRowsTitle(String searchWord);
    public int getSearchTotalRowsContent(String searchWord);
}


