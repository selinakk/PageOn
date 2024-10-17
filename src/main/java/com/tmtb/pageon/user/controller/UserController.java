package com.tmtb.pageon.user.controller;


import com.tmtb.pageon.user.service.UserService;
import com.tmtb.pageon.user.model.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/insertUserForm")
    public  String insertUserForm(@ModelAttribute("user") UserVO userVO){
        log.info("사용자 정보 전달");
        log.info(String.valueOf(userVO));
        userService.insertUser(userVO);
        return "redirect:/";
    }

    @PostMapping("/selectUser")
    @ResponseBody
    public String insertSelectfindUser(@RequestParam String id) {
        boolean isExist = userService.selectUser(id);
        if (isExist) {
            return "해당 아이디가 존재합니다";
        } else {
            return "해당 아이디는 사용 가능합니다";
        }
    }



}
