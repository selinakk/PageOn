package com.tmtb.pageon.notice.mapper;

import com.tmtb.pageon.notice.model.NoticeVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;


@Mapper
public interface NoticeMapper {


    public List<NoticeVO> selectAll();

    public List<NoticeVO> selectAllSortedPageBlock(int startRow, int pageBlock, String sort);

    public int getTotalRows();

    public List<NoticeVO> searchListPageBlockTitle(String searchWord, int startRow, int pageBlock, String sort);

    public List<NoticeVO> searchListPageBlockContent(String searchWord, int startRow, int pageBlock, String sort);

    public int getSearchTotalRowsTitle(String searchWord);

    public int getSearchTotalRowsContent(String searchWord);

    public NoticeVO selectOne(NoticeVO vo);

    public int insertOK(NoticeVO vo);

    public int updateOK(NoticeVO vo);

    public int deleteOK(NoticeVO vo);

    public void hitcountUpdate(NoticeVO vo);
}


