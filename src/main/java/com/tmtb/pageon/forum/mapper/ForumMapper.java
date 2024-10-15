package com.tmtb.pageon.forum.mapper;

import com.tmtb.pageon.forum.model.ForumVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ForumMapper {
    public List<ForumVO> getList(@Param("offset") int offset,
                                 @Param("size") int size,
                                 @Param("sortField") String sortField,
                                 @Param("sortDir") String sortDir);
    int getListCount();
}
