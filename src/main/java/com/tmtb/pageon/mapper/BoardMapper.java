package com.tmtb.pageon.mapper;


import com.tmtb.pageon.board.model.BoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    List<BoardVO> findAll();

}
