package com.tmtb.pageon.login.mapper;


import com.tmtb.pageon.login.model.LoginVO;
import com.tmtb.pageon.user.model.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
    LoginVO login(String id, String pw);
    public UserVO findById(String id);

}
