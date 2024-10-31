package com.tmtb.pageon.comment.mapper;

import com.tmtb.pageon.comment.model.CommentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    void insertOK(CommentVO vo);

    void updateOK(CommentVO vo);

    void deleteOK(int num);

    List<CommentVO> selectAll(String type, Integer bnum, Integer fnum, Integer rnum, int startRow, int pageBlock);

    int getTotalRows(String type, Integer bnum, Integer fnum, Integer rnum);

    List<CommentVO> selectAllChild(Integer cnum, int startRow, int pageBlock);

    int getTotalChildRows(Integer cnum);

    // 신고 상태 확인 메서드 추가
    int checkReport(int num);

    // 신고 상태 업데이트 메서드 추가
    void reportOK(int num);

}
