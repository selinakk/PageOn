package com.tmtb.pageon.user.service;

import com.tmtb.pageon.user.mapper.AdminMapper;
import com.tmtb.pageon.user.model.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminMapper mapper;

    public void insertUser(UserVO userVO) {
        mapper.insertMember(userVO);
    }


    public List<UserVO> selectAllMembers(int offset, int size) {
        return mapper.selectAllMembers(offset, size);
    }

    public int countAllMembers() {
        return mapper.countAllMembers();
    }

    public void updateUserInfo(UserVO userVO) {
        mapper.updateMember(userVO);
    }

    public void deleteUser(String id) {
        mapper.deleteMember(id);
    }

    public List<UserVO> searchMembers(String keyword, String sortOrder, int offset, int size) {
        return mapper.searchMembers(keyword, sortOrder, offset, size);
    }

    public int countSearchMembers(String keyword) {
        return mapper.countSearchMembers(keyword);
    }
}
