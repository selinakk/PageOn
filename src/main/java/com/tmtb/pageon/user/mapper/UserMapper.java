package com.tmtb.pageon.user.mapper;

import com.tmtb.pageon.user.model.BoardVO;
import com.tmtb.pageon.user.model.ForumVO;
import com.tmtb.pageon.user.model.ReviewVO;
import com.tmtb.pageon.user.model.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    // 사용자 정보 삽입
    void insertUser(UserVO userVO);

    // ID 중복 여부 확인
    int selectfindUser(String id);

    // 사용자 정보 조회
    UserVO findById(String id);

    List<ForumVO> findByforumPazing(String id, int offset, int size);

    List<BoardVO> findBoardsByUserPazing(String id, int offset, int size);

    List<ReviewVO> findByReviewsPazing(String id, int offset, int size);

    List<ForumVO> findByForum(String id);
    List<BoardVO> findByBoard(String id);
    List<ReviewVO> findByReviews(String id);
}
