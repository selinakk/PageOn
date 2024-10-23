package com.tmtb.pageon.user.mapper;

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
}
