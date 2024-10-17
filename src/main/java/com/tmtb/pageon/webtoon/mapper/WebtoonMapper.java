package com.tmtb.pageon.webtoon.mapper;

import com.tmtb.pageon.board.model.BoardVO;
import com.tmtb.pageon.webtoon.model.WebtoonVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WebtoonMapper {

    public List<WebtoonVO> getWebtoonList();

}
