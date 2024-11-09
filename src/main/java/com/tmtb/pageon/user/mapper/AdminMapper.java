package com.tmtb.pageon.user.mapper;

import com.tmtb.pageon.user.model.BoardVO;
import com.tmtb.pageon.user.model.ReviewVO;
import com.tmtb.pageon.user.model.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<UserVO> selectAllMembers(int offset, int size);
    int countAllMembers();
    void insertMember(UserVO user);
    void updateMember(UserVO user);
    void deleteMember(String id);
    List<UserVO> searchMembers(String keyword, String sortOrder, int offset, int size);
    int countSearchMembers(String keyword);
    // 검색된 게시물 조회
    List<BoardVO> searchReportedBoards(@Param("keyword") String keyword, @Param("limit") int limit, @Param("offset") int offset);

    // 검색된 리뷰 조회
    List<ReviewVO> searchReportedReviews(@Param("keyword") String keyword, @Param("limit") int limit, @Param("offset") int offset);

    // 검색된 게시물의 총 레코드 수
    int countSearchReportedBoards(@Param("keyword") String keyword);

    // 검색된 리뷰의 총 레코드 수
    int countSearchReportedReviews(@Param("keyword") String keyword);

    void deleteBoardById(@Param("num") int num);

    void deleteReviewById(@Param("num") int num);

}