package com.tmtb.pageon.comment.mapper;

import com.tmtb.pageon.comment.model.CommentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    void insertOK(CommentVO vo);

    void updateOK(CommentVO vo);

    void deleteOK(int num);

    List<CommentVO> selectAll(String type, Integer bnum, Integer fnum, Integer rnum);

    List<CommentVO> selectAllChild(int cnum);
}
