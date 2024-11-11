package com.tmtb.pageon.user.controller;

import com.tmtb.pageon.user.model.*;
import com.tmtb.pageon.user.service.MailService;
import com.tmtb.pageon.user.service.ProductService;
import com.tmtb.pageon.user.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Controller
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    @Autowired
    MailService mailService;


    @PostMapping("/insertUserForm")
    public String insertUserForm(@ModelAttribute("user") UserVO userVO, @RequestParam("imgFile") MultipartFile imgFile, Model model) throws MessagingException {
        if (userVO.getUser_role() == null || userVO.getUser_role().isEmpty()) {
            userVO.setUser_role("USER");
        }

        log.info("사용자 정보 전달: {}", userVO);

        String id = userVO.getId(); // 등록된 사용자 아이디
        String email = userVO.getEmail(); // 입력된 이메일

        // 이메일과 ID가 유효한지 확인
        if (email != null && !email.isEmpty()) {
            try {
                mailService.sendRegisterIdByEmail(email, id);
                userService.insertUser(userVO, imgFile);
                model.addAttribute("message", "회원가입이 완료되었습니다. 홈으로 가서 다시 로그인하세요.");
                log.info("회원가입이 완료되었습니다: {}", email);
            } catch (Exception e) {
                // 메일 전송 중 오류가 발생하면 사용자 등록 중단
                model.addAttribute("message", "회원가입 메일 전송 중 오류가 발생했습니다.");
                log.error("메일 전송 중 오류 발생: {}", e.getMessage());
                return "user/find-id-result"; // 결과 페이지로 이동
            }
        } else {
            model.addAttribute("message", "유효하지 않은 이메일입니다.");
            return "user/find-id-result"; // 결과 페이지로 이동
        }

        // 결과를 표시할 페이지로 이동
        return "user/find-id-result";
    }

    // 아이디 중복 체크
    @PostMapping("/selectUser")
    @ResponseBody
    public String insertSelectfindUser(@RequestParam String id) {
        boolean isExist = userService.selectUser(id);
        return isExist ? "해당 아이디가 존재합니다" : "해당 아이디는 사용 가능합니다";
    }

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
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "") String searchKeyword) {
        String id = (String) session.getAttribute("id");
        int size = 4; // 페이지당 항목 수
        int pageSize = 5; // 페이지네이션에서 한 번에 표시할 페이지 번호 수
        int offset = Math.max(0, page * size); // 오프셋 계산

        if (id != null) {
            int totalComments = 0;

            switch (type) {
                case "forum":
                    List<ForumVO> forumList = userService.findByForumPazing(id, offset, size, searchKeyword);
                    model.addAttribute("forumList", forumList);
                    totalComments = userService.countForumsByUser(id, searchKeyword);
                    break;
                case "board":
                    List<BoardVO> boardList = userService.findByBoardPazing(id, offset, size, searchKeyword);
                    model.addAttribute("boardList", boardList);
                    totalComments = userService.countBoardsByUser(id, searchKeyword);
                    break;
                case "review":
                    List<ReviewVO> reviewList = userService.findByReviewsPazing(id, offset, size, searchKeyword);
                    model.addAttribute("reviewList", reviewList);
                    totalComments = userService.countReviewsByUser(id, searchKeyword);
                    break;
                case "comment":
                    List<CommentVO> commentList = userService.findCommentsByUserPazing(id, offset, size, searchKeyword);
                    model.addAttribute("commentList", commentList);
                    totalComments = userService.countCommentsByUser(id, searchKeyword);
                    break;
                default:
                    return "redirect:/";
            }

            // 전체 페이지 수 계산
            int commentTotalPages = (int) Math.ceil((double) totalComments / size);

            // 현재 페이지 그룹 계산
            int currentPageGroup = page / pageSize;
            int startPage = currentPageGroup * pageSize;
            int endPage = Math.min(startPage + pageSize - 1, commentTotalPages - 1);

            model.addAttribute("type", type);
            model.addAttribute("currentPage", page);
            model.addAttribute("commentTotalPages", commentTotalPages);
            model.addAttribute("startPage", startPage);  // 시작 페이지
            model.addAttribute("endPage", endPage);      // 종료 페이지
            model.addAttribute("searchKeyword", searchKeyword);
        } else {
            return "redirect:/";
        }

        return "user/allProfile"; // 전체보기 페이지로 이동
    }



    @GetMapping("/user/profile/id/pazing/{type}/{id}")
    public String paramviewAllPazing(@PathVariable String type, @PathVariable String id,
                                     Model model,
                                     @RequestParam(defaultValue = "") String searchKeyword,
                                     @RequestParam(defaultValue = "0") int page) {
        int size = 4; // 페이지당 항목 수
        int pageSize = 5; // 페이지네이션에서 한 번에 표시할 페이지 번호 수
        int offset = Math.max(0, page * size); // 오프셋 계산: 음수일 경우 0으로 설정

        if (id != null) {
            int totalComments = 0;
            switch (type) {
                case "forum":
                    List<ForumVO> forumList = userService.findByForumPazing(id, offset, size, searchKeyword); // 포럼 데이터 조회
                    model.addAttribute("forumList", forumList);
                    totalComments = userService.countForumsByUser(id, searchKeyword); // 전체 댓글 수를 구하는 서비스 메서드
                    break;
                case "board":
                    List<BoardVO> boardList = userService.findByBoardPazing(id, offset, size, searchKeyword); // 게시판 데이터 조회
                    model.addAttribute("boardList", boardList);
                    totalComments = userService.countBoardsByUser(id, searchKeyword); // 전체 댓글 수를 구하는 서비스 메서드
                    break;
                case "review":
                    List<ReviewVO> reviewList = userService.findByReviewsPazing(id, offset, size, searchKeyword); // 리뷰 데이터 조회
                    model.addAttribute("reviewList", reviewList);
                    totalComments = userService.countReviewsByUser(id, searchKeyword); // 전체 댓글 수를 구하는 서비스 메서드
                    break;
                case "comment":
                    List<CommentVO> commentList = userService.findCommentsByUserPazing(id, offset, size, searchKeyword); // 댓글 데이터 조회
                    model.addAttribute("commentList", commentList);
                    totalComments = userService.countCommentsByUser(id, searchKeyword); // 전체 댓글 수를 구하는 서비스 메서드
                    break;
                default:
                    return "redirect:/";
            }

            // 전체 페이지 수 계산
            int commentTotalPages = (int) Math.ceil((double) totalComments / size);

            // 페이지 그룹의 시작과 끝 계산
            int currentPageGroup = page / pageSize;
            int startPage = currentPageGroup * pageSize;
            int endPage = Math.min(startPage + pageSize - 1, commentTotalPages - 1);

            // 페이지 그룹에 맞는 정보를 모델에 추가
            model.addAttribute("type", type);
            model.addAttribute("currentPage", page);
            model.addAttribute("commentTotalPages", commentTotalPages);
            model.addAttribute("startPage", startPage); // 시작 페이지
            model.addAttribute("endPage", endPage); // 끝 페이지
            model.addAttribute("searchKeyword", searchKeyword);
        } else {
            return "redirect:/";
        }

        return "user/allNameProfile"; // 전체보기 페이지로 이동
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

    @PostMapping("/user/updateCategories")
    public String updateUserCategories(@RequestParam(name = "likeCategories", required = false) List<String> likeCategories, HttpSession session) {
        // 파라미터가 없으면 빈 리스트로 처리
        if (likeCategories == null) {
            likeCategories = new ArrayList<>();
        }

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

    // 로그인이 필요한 요청경로를 로그인 하지 않은 상태로 요청하면 리다일렉트 되는 요청경로
    @GetMapping("/user/required_login")
    public String required_login() {
        return "/user/required_login";
    }


    @PostMapping("/user/login_fail")
    public String login_fail() {
        // 로그인 실패임을 알릴 페이지
        return "user/login_fail";
    }

    @PostMapping("/user/login_success")
    public String login_success() {
        // 로그인 성공후 보여질 페이지
        return "user/login_success";
    }

    //김시윤 추가
    @GetMapping("/user/login")
    public String login() {
        return "user/login-form";
    }

    // 세션 허용갯수 초과시
    @GetMapping("/user/expired")
    public String expired() {
        return "user/expired";
    }

    @GetMapping("/user/denied")
    public String user_denied() {
        // 로그인 실패임을 알릴 페이지
        return "user/denied";
    }


    @PostMapping("/updatePassword")
    public String updatePassword(@RequestParam String id, @RequestParam String pw, @RequestParam String email, Model model) {
        if (userService.selectfindPw(id, email)) {
            userService.updatePassword(id, pw, email);
            model.addAttribute("message", "비밀번호가 성공적으로 변경되었습니다!");
            return "user/success-pwupdate"; // 성공 페이지
        } else {
            model.addAttribute("message", "아이디와 이메일이 일치하지 않습니다. 다시 시도해 주세요.");
            return "user/fail-pwupdate"; // 실패 페이지
        }
    }

    @PostMapping("/find-id")
    public String findUserId(@RequestParam("email") String email, Model model) {
        // DB에서 이메일로 아이디를 조회
        String id = userService.findUserIdByEmail(email);

        if (id != null) {
            try {
                // 아이디 이메일 전송
                mailService.sendIdByEmail(email, id);
                model.addAttribute("message", "이메일로 아이디가 전송되었습니다.");
            } catch (Exception e) {
                model.addAttribute("message", "메일 전송 중 오류가 발생했습니다.");
            }
        } else {
            model.addAttribute("message", "해당 이메일로 등록된 아이디가 없습니다.");
        }

        return "user/find-id-result"; // 결과를 표시할 페이지
    }


    // 아이디 중복 체크
    @PostMapping("/selectEmail")
    @ResponseBody
    public String insertSelectfindEmail(@RequestParam String email) {
        boolean isExist = userService.selectfindEmail(email);
        return isExist ? "해당 이메일이 존재합니다" : "해당 이메일은 사용 가능합니다";
    }
    @GetMapping( "/user/post-fail")
    public String user_postfail() {
        // 로그인 실패임을 알릴 페이지
        return "user/post-fail";
    }
}

