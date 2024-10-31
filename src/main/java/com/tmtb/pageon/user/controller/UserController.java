package com.tmtb.pageon.user.controller;

import com.tmtb.pageon.user.model.*;
import com.tmtb.pageon.user.service.ProductService;
import com.tmtb.pageon.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;

    // 사용자 등록
    @PostMapping("/insertUserForm")
    public String insertUserForm(@ModelAttribute("user") UserVO userVO, @RequestParam("imgFile") MultipartFile imgFile) {
        log.info("사용자 정보 전달: {}", userVO);
        userService.insertUser(userVO, imgFile);
        return "redirect:/"; // 리다이렉트
    }

    // 아이디 중복 체크
    @PostMapping("/selectUser")
    @ResponseBody
    public String insertSelectfindUser(@RequestParam String id) {
        boolean isExist = userService.selectUser(id);
        return isExist ? "해당 아이디가 존재합니다" : "해당 아이디는 사용 가능합니다";
    }

    // 사용자 프로필 조회
    // 사용자 프로필 조회
    @GetMapping("/user/profile")
    public String findById(HttpSession session, Model model) {
        String id = (String) session.getAttribute("id");
        log.info("세션에서 가져온 id: " + id);

        if (id != null) {
            UserVO userVO = userService.findById(id);
            List<ForumVO> forumList = userService.findByForum(id);
            List<BoardVO> boardList = userService.findByBoard(id);
            List<ReviewVO> reviewList = userService.findByReviews(id);
            List<CommentVO> commentList = userService.findByComment(id);


            // 최근 본 항목 조회
            List<Object> recentItems = productService.getRecentItems(id); // ID에 따른 최근 본 항목 가져오기

            // 사용자 선호 카테고리와 다른 데이터 추가
            String[] categories = userVO.getLike_categories().split(",");
            model.addAttribute("categories", categories);
            model.addAttribute("userVO", userVO);
            model.addAttribute("forumList", forumList);
            model.addAttribute("boardList", boardList);
            model.addAttribute("reviewList", reviewList);
            model.addAttribute("commentList", commentList);
            model.addAttribute("recentItems", recentItems); // 최근 본 항목 추가
        } else {
            return "redirect:/";
        }
        return "user/profile";
    }

    // 사용자 프로필 조회
    @GetMapping("/user/userprofile")
    public String userfindById(@RequestParam("id") String id, Model model) {
        if (id != null) {
            UserVO userVO = userService.findById(id);
            List<ForumVO> forumList = userService.findByForum(id);
            List<BoardVO> boardList = userService.findByBoard(id);
            List<ReviewVO> reviewList = userService.findByReviews(id);
            List<CommentVO> commentList = userService.findByComment(id);

            // 사용자 선호 카테고리와 다른 데이터 추가
            String[] categories = userVO.getLike_categories().split(",");
            model.addAttribute("categories", categories);
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


    // 사용자 정보 업데이트
    @PostMapping("/user/update")
    public String updateUserInfo(@ModelAttribute UserVO user, HttpSession session, @RequestParam("imgFile") MultipartFile imgFile) {


        String id = (String) session.getAttribute("id");
        if (id != null) {
            user.setId(id);
            userService.updateUserInfo(user, imgFile);
            return "redirect:/user/profile";
        } else {
            return "redirect:/";
        }
    }

    // 카테고리 업데이트
    @PostMapping("/user/updateCategories")
    public String updateUserCategories(@RequestParam List<String> likeCategories, HttpSession session) {
        String id = (String) session.getAttribute("id");
        log.info("Received categories: {}", likeCategories);
        userService.updateUserCategories(id, String.join(",", likeCategories));
        return "redirect:/user/profile";
    }

    // 사용자 프로필 수정 페이지 이동
    @GetMapping("/user/updateProfile")
    public String updateUserProfile(HttpSession session, Model model) {
        String id = (String) session.getAttribute("id");
        if (id != null) {
            UserVO userVO = userService.findById(id);
            model.addAttribute("userVO", userVO);
            return "user/updateProfile";
        } else {
            return "redirect:/";
        }
    }

    // 카테고리 수정 페이지 이동
    @GetMapping("/user/updateCategoriesPage")
    public String updateUserCategoriesPage(HttpSession session, Model model) {
        String id = (String) session.getAttribute("id");
        if (id != null) {
            UserVO userVO = userService.findById(id);
            model.addAttribute("likeCategories", userVO.getLike_categories().split(","));
            return "user/updateCategories";
        } else {
            return "redirect:/";
        }
    }


}