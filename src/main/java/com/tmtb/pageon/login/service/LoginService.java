package com.tmtb.pageon.login.service;

import com.tmtb.pageon.login.mapper.LoginMapper;
import com.tmtb.pageon.login.model.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private LoginMapper loginMapper;


    public String login(String id, String pw){
        LoginVO loginVO = loginMapper.login(id,pw);

        if (loginVO != null) {
            return loginVO.getId();
        }
        return null;
    }

}