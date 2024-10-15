package com.tmtb.pageon.user.mapper;

import com.tmtb.pageon.user.model.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    public void insertUser(UserVO userVO);


}