package com.tmtb.pageon.forum.mapper;

import com.tmtb.pageon.forum.model.ForumVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ForumMapper {
    List<ForumVO> getList(@Param("offset") int offset,
                                 @Param("size") int size,
                                 @Param("sortField") String sortField,
                                 @Param("sortDir") String sortDir);
    int getListCnt();
    List<ForumVO> searchForum(@Param("searchKey") String searchKey,
                                     @Param("searchWord") String searchWord,
                                    @Param("offset") int offset,
                                    @Param("size") int size);
    int searchForumCnt(@Param("searchKey") String searchKey,
                         @Param("searchWord") String searchWord);
    ForumVO selectOne(ForumVO vo);
    //
    boolean insertForumOK(ForumVO vo);
    boolean updateForumOK(ForumVO vo);
    boolean deleteForumOK(ForumVO vo);
    void reportForumOK(@Param("num") int num);
    void increaseForumHit(@Param("num") int num);
}
