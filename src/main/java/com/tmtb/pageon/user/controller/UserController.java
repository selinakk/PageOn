package com.tmtb.pageon.user.controller;


import com.tmtb.pageon.user.service.UserService;
import com.tmtb.pageon.user.model.UserVO;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class UserController {

    @Autowired
    UserService userService;


    // POST 요청: 사용자 등록 정보를 처리하는 메서드
    @PostMapping("/insertUserForm")
    public String insertUserForm(@ModelAttribute("user") UserVO userVO) {
        log.info("사용자 정보 전달");
        log.info(String.valueOf(userVO));
        userService.insertUser(userVO); // 사용자 정보 저장
        return "redirect:/"; // 성공 후 리디렉션
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


    @GetMapping("/user/profile")
    public String findById(HttpSession session,Model model){
        String id = (String) session.getAttribute("id");
        log.info("세션에서 가져온 id: " + id);
        if(id != null){
            UserVO userVO = userService.findById(id);
            model.addAttribute("userVO",userVO);
        }else {
            return "redirect:/";
        }

        return "user/profile";
    }



}
