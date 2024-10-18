package com.tmtb.pageon.user.mapper;

import com.tmtb.pageon.user.model.BoardVO;
import com.tmtb.pageon.user.model.ForumVO;
import com.tmtb.pageon.user.model.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    public void insertUser(UserVO userVO);

    int selectfindUser(String id);

    UserVO findById(String id);


    List<ForumVO> findByforum(String id);

    List<BoardVO> findByboard(String id);

}