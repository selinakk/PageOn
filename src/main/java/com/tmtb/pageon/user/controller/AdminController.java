package com.tmtb.pageon.user.controller;

import com.tmtb.pageon.user.model.UserVO;
import com.tmtb.pageon.user.service.AdminService;
import com.tmtb.pageon.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
            Model model) {

        int offset = page * size;
        List<UserVO> members = adminService.selectAllMembers(offset, size);
        int totalMembers = adminService.countAllMembers();
        int totalPages = (int) Math.ceil((double) totalMembers / size);

        model.addAttribute("members", members);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "admin/memberList";  // 뷰의 이름
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
        return "admin/memberForm";  // 사용자 수정 폼
    }

    @PostMapping("/members/update")
    public String updateMember(UserVO userVO) {
        adminService.updateUserInfo(userVO);
        return "redirect:/members";
    }

    @GetMapping("/members/delete")
    public String deleteMember(@RequestParam String id) {
        adminService.deleteUser(id);
        return "redirect:/members";
    }
}
