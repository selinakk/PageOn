package com.tmtb.pageon.login.controller;


import com.tmtb.pageon.login.model.LoginVO;
import com.tmtb.pageon.login.service.LoginService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Slf4j
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public String login(@ModelAttribute LoginVO loginVO, Model model, HttpSession httpSession) {
        // ID와 PW가 비어있는지 확인
        if (loginVO.getId() == null || loginVO.getId().isEmpty() || loginVO.getPw() == null || loginVO.getPw().isEmpty()) {
            model.addAttribute("error", "아이디와 비밀번호를 모두 입력해야 합니다.");
            return "login"; // 로그인 페이지의 뷰 이름을 반환
        }

        String login = loginService.login(loginVO.getId(), loginVO.getPw());

        if (login != null) {
            httpSession.setAttribute("id", login);
            log.info("로그인 기능 성공");
            log.info("로그인 성공, 세션에 id 저장: " + login);
            return "redirect:/"; // 메인 페이지로 리다이렉트
        } else {
            model.addAttribute("error", "로그인 아이디가 없습니다. 다시 시도해주세요.");
            return "login"; // 로그인 페이지의 뷰 이름을 반환
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/"; // 로그인 페이지로 리다이렉트
    }

}
