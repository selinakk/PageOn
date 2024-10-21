package com.tmtb.pageon.user.controller;

import com.tmtb.pageon.user.model.BoardVO;
import com.tmtb.pageon.user.model.ForumVO;
import com.tmtb.pageon.user.model.ReviewVO;
import com.tmtb.pageon.user.service.UserService;
import com.tmtb.pageon.user.model.UserVO;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
public class UserController {

    @Autowired
    UserService userService;

    // POST 요청: 사용자 등록 정보를 처리하는 메서드
    @PostMapping("/insertUserForm")
    public String insertUserForm(@ModelAttribute("user") UserVO userVO) {
        log.info("사용자 정보 전달: {}", userVO);
        userService.insertUser(userVO); // 사용자 정보 저장
        return "redirect:/"; // 성공 후 리디렉션
    }

    @PostMapping("/selectUser")
    @ResponseBody
    public String insertSelectfindUser(@RequestParam String id) {
        boolean isExist = userService.selectUser(id);
        return isExist ? "해당 아이디가 존재합니다" : "해당 아이디는 사용 가능합니다";
    }

    @GetMapping("/user/profile")
    public String findById(HttpSession session, Model model) {
        String id = (String) session.getAttribute("id");
        log.info("세션에서 가져온 id: " + id);
        if (id != null) {
            UserVO userVO = userService.findById(id);
            List<ForumVO> forumList = userService.findByForum(id); // 포럼 데이터 조회
            List<BoardVO> boardList = userService.findByBoard(id); // 게시판 데이터 조회
            List<ReviewVO> reviewList = userService.findByReviews(id); // 리뷰 데이터 조회

            model.addAttribute("userVO", userVO);
            model.addAttribute("forumList", forumList);
            model.addAttribute("boardList", boardList);
            model.addAttribute("reviewList", reviewList);
        } else {
            return "redirect:/";
        }
        return "user/profile";
    }

    @GetMapping("/user/profile/all/pazing/{type}")
    public String viewAllPazing(@PathVariable String type, HttpSession session, Model model,
                                @RequestParam(defaultValue = "0") int page) {
        String id = (String) session.getAttribute("id");
        int size = 4; // 페이지당 항목 수
        int offset = Math.max(0, page * size); // 오프셋 계산: 음수일 경우 0으로 설정

        if (id != null) {
            switch (type) {
                case "forum":
                    List<ForumVO> forumList = userService.findByForumPazing(id, offset, size); // 포럼 데이터 조회
                    model.addAttribute("forumList", forumList);
                    break;
                case "board":
                    List<BoardVO> boardList = userService.findByBoardPazing(id, offset, size); // 게시판 데이터 조회
                    model.addAttribute("boardList", boardList);
                    break;
                case "review":
                    List<ReviewVO> reviewList = userService.findByReviewsPazing(id, offset, size); // 리뷰 데이터 조회
                    model.addAttribute("reviewList", reviewList);
                    break;
                default:
                    return "redirect:/";
            }
            model.addAttribute("type", type);
            model.addAttribute("currentPage", page);
        } else {
            return "redirect:/";
        }
        return "user/allProfile"; // 전체보기 페이지로 이동
    }
}

