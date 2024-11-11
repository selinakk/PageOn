package com.tmtb.pageon.config;

import com.tmtb.pageon.user.model.UserVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class CustomUser extends User {
    private UserVO userVO;

    public CustomUser(UserVO userVO, String username, String password, List<GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userVO = userVO;
    }

    public UserVO getUserVO() {
        return userVO;
    }
}
