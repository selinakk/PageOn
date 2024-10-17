package com.tmtb.pageon.board.mapper;


import com.tmtb.pageon.board.model.BoardVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {

    public List<BoardVO> selectFreeAll();

    public List<BoardVO> selectQnaAll();

    public int insertOK(BoardVO vo);

    public BoardVO selectOne(BoardVO vo);

    public int deleteOK(BoardVO vo);

    public int updateOK(BoardVO vo);

    public int updateBoardHitCount(BoardVO vo);


    //조회수 관련
    List<BoardVO> getFreeBoardListByHitCount(@Param("offset") int offset, @Param("pageSize") int pageSize);
    List<BoardVO> getQnaBoardListByHitCount(@Param("offset") int offset, @Param("pageSize") int pageSize);

    //페이징 관련
    List<BoardVO> getFreeBoardList(@Param("offset") int offset, @Param("pageSize") int pageSize);
    List<BoardVO> getQnaBoardList(@Param("offset") int offset, @Param("pageSize") int pageSize);
    int getTotalCount();

    //검색 관련 + 페이징
    List<BoardVO> searchBoardByTitle(@Param("searchWord") String searchWord, @Param("category") String category,
                                     @Param("offset") int offset, @Param("pageSize") int pageSize);
    List<BoardVO> searchBoardByContent(@Param("searchWord") String searchWord, @Param("category") String category,
                                       @Param("offset") int offset, @Param("pageSize") int pageSize);

    int getTotalCountByTitle(@Param("searchWord") String searchWord, @Param("category") String category);
    int getTotalCountByContent(@Param("searchWord") String searchWord, @Param("category") String category);

    void updateReportStatus(int num);
}
