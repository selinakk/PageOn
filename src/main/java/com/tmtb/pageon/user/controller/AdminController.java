package com.tmtb.pageon.user.controller;

import com.tmtb.pageon.user.model.BoardVO;
import com.tmtb.pageon.user.model.ReviewVO;
import com.tmtb.pageon.user.model.UserVO;
import com.tmtb.pageon.user.service.AdminService;
import com.tmtb.pageon.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @GetMapping("/members")
    public String listMembers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "ASC") String sortOrder,
            Model model) {

        int offset = page * size;
        List<UserVO> members = adminService.searchMembers(keyword, sortOrder, offset, size);
        int totalMembers = adminService.countSearchMembers(keyword);
        int totalPages = (int) Math.ceil((double) totalMembers / size);

        model.addAttribute("members", members);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("keyword", keyword); // 검색 키워드 유지
        model.addAttribute("sortOrder", sortOrder); // 정렬 기준 유지

        return "admin/memberList"; // 뷰의 이름
    }

    @GetMapping("/members/new")
    public String newMemberForm(Model model) {
        model.addAttribute("userVO", new UserVO());
        return "admin/memberForm";  // 사용자 추가 폼
    }

    @PostMapping("/members")
    public String createMember(UserVO userVO) {
        adminService.insertUser(userVO);
        return "redirect:/members";
    }

    @GetMapping("/members/edit")
    public String editMemberForm(@RequestParam String id, Model model) {
        UserVO user = userService.findById(id);
        model.addAttribute("userVO", user);
        return "admin/memberForm";  // 수정 폼으로 이동
    }

    @PostMapping("/members/update")
    public String updateMember(@ModelAttribute UserVO user,  @RequestParam("imgFile") MultipartFile imgFile) throws IOException {
        adminService.updateUserInfo(user,imgFile);
        return "redirect:/members";
    }

    @GetMapping("/members/delete")
    public String deleteMember(@RequestParam String id) {
        adminService.deleteUser(id);
        return "redirect:/members";
    }


    @GetMapping("/members/reported")
    public String getReportedContent(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "boards") String type,
            @RequestParam(defaultValue = "") String keyword,
            Model model) {

        if (type.equals("boards")) {
            List<BoardVO> boards = adminService.getReportedBoards(page, size, keyword);
            int totalBoardPages = adminService.getBoardTotalPages(size, keyword);
            model.addAttribute("boards", boards);
            model.addAttribute("totalBoardPages", totalBoardPages);
        } else if (type.equals("reviews")) {
            List<ReviewVO> reviews = adminService.getReportedReviews(page, size, keyword);
            int totalReviewPages = adminService.getReviewTotalPages(size, keyword);
            model.addAttribute("reviews", reviews);
            model.addAttribute("totalReviewPages", totalReviewPages);
        }

        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("type", type);
        model.addAttribute("keyword", keyword);

        return "admin/reportList"; // Thymeleaf 템플릿 경로
    }
    @PostMapping("/boards/delete")
    public String deleteBoard(@RequestParam int num) {
        adminService.deleteBoard(num);
        return "redirect:/members/reported?type=boards";
    }

    @PostMapping("/reviews/delete")
    public String deleteReview(@RequestParam int num) {
        adminService.deleteReview(num);
        return "redirect:/members/reported?type=reviews";
    }
}