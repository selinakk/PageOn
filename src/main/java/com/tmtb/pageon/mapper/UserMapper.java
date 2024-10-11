package com.tmtb.pageon.mapper;

import com.tmtb.pageon.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findAll();
}
