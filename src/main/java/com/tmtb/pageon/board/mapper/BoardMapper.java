package com.tmtb.pageon.board.mapper;


import com.tmtb.pageon.board.model.BoardVO;
import org.apache.ibatis.annotations.Mapper;

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
}
