package com.tmtb.pageon.user.controller;

import com.tmtb.pageon.user.model.*;
import com.tmtb.pageon.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
        userService.insertUser(userVO);
        return "redirect:/";
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
            List<CommentVO> commentList = userService.findByComment(id);

            // 사용자 카테고리 정보 추가
            String[] categories = userVO.getLike_categories().split(","); // 카테고리를 배열로 변환
            model.addAttribute("categories", categories); // 카테고리 목록을 모델에 추가

            model.addAttribute("userVO", userVO);
            model.addAttribute("forumList", forumList);
            model.addAttribute("boardList", boardList);
            model.addAttribute("reviewList", reviewList);
            model.addAttribute("commentList", commentList);
        } else {
            return "redirect:/";
        }
        return "user/profile";
    }

    @GetMapping("/user/userprofile")
    public String findByIdUser(@RequestParam("id") String id, Model model) {
        log.info("URL 파라미터에서 가져온 id: " + id);

        // id 값이 존재할 경우 처리
        if (id != null && !id.isEmpty()) {
            // 사용자 정보 조회
            UserVO userVO = userService.findById(id);

            // 포럼, 게시판, 리뷰, 댓글 데이터 조회
            List<ForumVO> forumList = userService.findByForum(id);
            List<BoardVO> boardList = userService.findByBoard(id);
            List<ReviewVO> reviewList = userService.findByReviews(id);
            List<CommentVO> commentList = userService.findByComment(id);

            // 사용자 선호 카테고리 처리
            if (userVO.getLike_categories() != null && !userVO.getLike_categories().isEmpty()) {
                String[] categories = userVO.getLike_categories().split(","); // 카테고리 문자열을 배열로 변환
                model.addAttribute("categories", categories); // 카테고리 배열을 모델에 추가
            }

            // 모델에 필요한 데이터 추가
            model.addAttribute("userVO", userVO);
            model.addAttribute("forumList", forumList);
            model.addAttribute("boardList", boardList);
            model.addAttribute("reviewList", reviewList);
            model.addAttribute("commentList", commentList);

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
                case "comment":
                    List<CommentVO> commentList = userService.findCommentsByUserPazing(id, offset, size); // 리뷰 데이터 조회
                    model.addAttribute("commentList", commentList);
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

    @PostMapping("user/update")
    public String updateUserInfo(@ModelAttribute UserVO user, HttpSession session) {
        String id = (String) session.getAttribute("id");
        user.setId(id);
        userService.updateUserInfo(user);
        log.info("udpate");
        return "redirect:/user/profile";
    }

    @PostMapping("user/updateCategories")
    public String updateUserCategories(@RequestParam List<String> likeCategories, HttpSession session) {
        String id = (String) session.getAttribute("id");

        // 로그 추가
        log.info("Received categories: {}", likeCategories); // 수신된 카테고리 확인

        userService.updateUserCategories(id, String.join(",", likeCategories)); // 카테고리 리스트를 콤마로 구분된 문자열로 변환
        return "redirect:/user/profile"; // 성공 후 리디렉션
    }

    @GetMapping("/user/updateProfile")
    public String updateUserProfile(HttpSession session, Model model) {
        String id = (String) session.getAttribute("id");
        if (id != null) {
            UserVO userVO = userService.findById(id);
            model.addAttribute("userVO", userVO);
            return "user/updateProfile"; // 사용자 정보 수정 페이지
        } else {
            return "redirect:/";
        }
    }


    @GetMapping("/user/updateCategoriesPage")
    public String updateUserCategoriesPage(HttpSession session, Model model) {
        String id = (String) session.getAttribute("id");
        if (id != null) {
            // 여기에 사용자의 현재 카테고리를 가져오는 로직을 추가합니다.
            UserVO userVO = userService.findById(id);
            model.addAttribute("likeCategories", userVO.getLike_categories().split(",")); // 현재 카테고리
            return "user/updateCategories"; // 카테고리 수정 페이지
        } else {
            return "redirect:/";
        }

    }

}

