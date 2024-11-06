package com.tmtb.pageon.notice.controller;

import com.tmtb.pageon.notice.model.NoticeVO;
import com.tmtb.pageon.notice.service.NoticeService;
import com.tmtb.pageon.user.model.UserVO;
import com.tmtb.pageon.user.service.UserService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@Slf4j
@Controller
public class NoticeController {

    @Autowired
    NoticeService service;

//    @Value("${file.dir}")
//    private String uploadDir;

    @Value("${file.dir}")
    private String realPath;

    @Autowired
    ServletContext context;


    @GetMapping("/notice/n_selectAll.do")
    public String selectAllSorted(
            Model model,
            HttpSession session, // 세션 추가
            @RequestParam(defaultValue = "1") int cpage,
            @RequestParam(defaultValue = "15") int pageBlock,
            @RequestParam(defaultValue = "newest") String sort,
            @RequestParam(defaultValue = "") String searchWord // 검색어 추가
    ) {

        String id = (String) session.getAttribute("id");
        log.info("세션에서 가져온 id: " + id);

        // 세션에서 사용자 정보 가져오기
        //userVO = (UserVO) session.getAttribute("user");
        String user_role = (String) session.getAttribute("user_role");

        //log.info("user:{}", user);
        //boolean isAdmin = (user != null && "ADMIN".equals(user.getUser_role())); // ADMIN인지 확인

        //log.info("isAdmin:{}", isAdmin);

        List<NoticeVO> list;
        int totalRows;

        // 검색어가 비어있으면 전체 목록 조회
        if (searchWord == null || searchWord.isEmpty()) {
            list = service.selectAllSortedPageBlock(cpage, pageBlock, sort);
            totalRows = service.getTotalRows();
        } else {
            // 검색어가 있을 경우 검색 목록 조회
            list = service.searchListPageBlock("id", searchWord, cpage, pageBlock, sort);
            totalRows = service.getSearchTotalRows("id", searchWord);
        }

        int totalPageCount = (totalRows + pageBlock - 1) / pageBlock;

        model.addAttribute("list", list);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("currentSort", sort);
        model.addAttribute("currentSearchWord", searchWord); // 검색어 모델에 추가
        //model.addAttribute("isAdmin", isAdmin); // ADMIN 여부 모델에 추가
        model.addAttribute("id", id); // admin 여부 모델에 추가
        //model.addAttribute("user_role", user_role); // ADMIN 여부 모델에 추가

        return "notice/selectAll";
    }



    // 공지사항 검색 조회 페이징
    @GetMapping("/notice/n_searchList.do")
    public String searchList(
            Model model,
            @RequestParam(defaultValue = "id") String searchKey,
            @RequestParam(defaultValue = "") String searchWord,
            @RequestParam(defaultValue = "1") int cpage,
            @RequestParam(defaultValue = "15") int pageBlock,
            @RequestParam(defaultValue = "newest") String sort,
            HttpSession session
    ) {
        List<NoticeVO> list = service.searchListPageBlock(searchKey, searchWord, cpage, pageBlock, sort);
        int totalRows = service.getSearchTotalRows(searchKey, searchWord);
        int totalPageCount = (totalRows + pageBlock - 1) / pageBlock;

        String id = (String) session.getAttribute("id"); // 세션에서 id 가져오기
        model.addAttribute("id", id);

        model.addAttribute("list", list);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("currentSort", sort);
        model.addAttribute("currentSearchKey", searchKey);
        model.addAttribute("currentSearchWord", searchWord);

        return "notice/selectAll";
    }



    // 공지사항 상세보기
    @GetMapping("/notice/n_selectOne.do")
    public String selectOne(NoticeVO vo, Model model, HttpSession session) {
        log.info("/notice/n_selectOne.do");
        log.info("vo:{}", vo);

        NoticeVO vo2 = service.selectOne(vo);
        log.info("vo2:{}", vo2);

        String id = (String) session.getAttribute("id");
        log.info("세션에서 가져온 id: " + id);

        model.addAttribute("id", id); // admin 여부 모델에 추가
        model.addAttribute("vo2", vo2);
        return "notice/selectOne";
    }



    // 공지사항 작성 폼
    @GetMapping("/notice/n_insert.do")
    public String insert(Model model, HttpSession session) {
        log.info("/notice/n_insert.do");

        String id = (String) session.getAttribute("id");
        log.info("세션에서 가져온 id: " + id);

        model.addAttribute("id", id); // admin 여부 모델에 추가

        return "notice/insert";
    }



    // 공지사항 작성
    @PostMapping("/notice/n_insertOK.do")
    public String insertOK(NoticeVO vo,  HttpServletRequest request) throws IllegalStateException, IOException {
        log.info("/notice/n_insertOK.do");
        log.info("vo:{}", vo);

//        // 이미지 파일 업로드 처리
//        MultipartFile file = vo.getFile(); // NoticeVO에 MultipartFile 필드 추가 필요
//        if (file != null && !file.isEmpty()) {
//            // 저장할 경로 설정 (서버의 절대 경로를 지정)
//            String uploadDir = request.getServletContext().getRealPath("/upload/");
//            File uploadDirPath = new File(uploadDir);
//            if (!uploadDirPath.exists()) {
//                uploadDirPath.mkdirs(); // 디렉토리 없을 시 생성
//            }
//
//            // 파일 저장
//            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
//            File uploadFile = new File(uploadDir, fileName);
//            file.transferTo(uploadFile);
//
//            // VO에 이미지 파일 이름 설정
//            vo.setImg_name(fileName);
//        }


        log.info(realPath);

        String originName = vo.getFile().getOriginalFilename();
        log.info("originName:{}", originName);

        if (originName.length() == 0) {// 넘어온 파일이 없을때 default.png 할당
            vo.setImg_name("default.png");
        } else {
            // 중복이미지 이름을 배제하기위한 처리
            String save_name = "img_" + System.currentTimeMillis() + originName.substring(originName.lastIndexOf("."));
            log.info("save_name:{}", save_name);
            vo.setImg_name(save_name);

            File f = new File(realPath, save_name);
            vo.getFile().transferTo(f);

            //// create thumbnail image/////////
            BufferedImage original_buffer_img = ImageIO.read(f);
            BufferedImage thumb_buffer_img = new BufferedImage(50, 50, BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D graphic = thumb_buffer_img.createGraphics();
            graphic.drawImage(original_buffer_img, 0, 0, 50, 50, null);

            File thumb_file = new File(realPath, "thumb_" + save_name);

            ImageIO.write(thumb_buffer_img, save_name.substring(save_name.lastIndexOf(".") + 1), thumb_file);

        }


        // 서비스 호출 및 결과 처리
        int result = service.insertOK(vo);
        log.info("result:{}", result);
        if (result == 1) {
            return "redirect:/notice/n_selectAll.do";
        } else {
            return "redirect:/notice/n_insert.do";
        }
    }



    // 공지사항 수정 폼
    @GetMapping("/notice/n_update.do")
    public String update(NoticeVO vo, Model model) {
        log.info("/notice/n_update.do");
        log.info("vo:{}", vo);

        NoticeVO vo2 = service.selectOne(vo);
        log.info("vo2:{}", vo2);

        model.addAttribute("vo2", vo2);

        return "notice/update";
    }



    // 공지사항 수정
    @PostMapping("/notice/n_updateOK.do")
    public String updateOK(NoticeVO vo, HttpServletRequest request) throws IllegalStateException, IOException {
        log.info("/notice/n_updateOK.do");
        log.info("vo:{}", vo);

//        MultipartFile file = vo.getFile();
//        if (file != null && !file.isEmpty()) {
//            String uploadDir = request.getServletContext().getRealPath("/upload/");
//            File uploadDirPath = new File(uploadDir);
//            if (!uploadDirPath.exists()) {
//                uploadDirPath.mkdirs();
//            }
//
//            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
//            File uploadFile = new File(uploadDir, fileName);
//            file.transferTo(uploadFile);
//
//            vo.setImg_name(fileName);
//        }


        log.info(realPath);

        String originName = vo.getFile().getOriginalFilename();
        log.info("originName:{}", originName);

        if (originName.length() == 0) {// 넘어온 파일이 없을때 default.png 할당
            vo.setImg_name(vo.getImg_name());
        } else {
            // 중복이미지 이름을 배제하기위한 처리
            String save_name = "img_" + System.currentTimeMillis() + originName.substring(originName.lastIndexOf("."));
            log.info("save_name:{}", save_name);
            vo.setImg_name(save_name);

            File f = new File(realPath, save_name);
            vo.getFile().transferTo(f);

            //// create thumbnail image/////////
            BufferedImage original_buffer_img = ImageIO.read(f);
            BufferedImage thumb_buffer_img = new BufferedImage(50, 50, BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D graphic = thumb_buffer_img.createGraphics();
            graphic.drawImage(original_buffer_img, 0, 0, 50, 50, null);

            File thumb_file = new File(realPath, "thumb_" + save_name);

            ImageIO.write(thumb_buffer_img, save_name.substring(save_name.lastIndexOf(".") + 1), thumb_file);

        }


        int result = service.updateOK(vo);
        log.info("result:{}", result);
        return result == 1 ? "redirect:/notice/n_selectOne.do?num=" + vo.getNum() : "redirect:/notice/n_update.do?num=" + vo.getNum();
    }



    //공지사항 삭제 폼
    @GetMapping("/notice/n_delete.do")
    public String delete() {
        log.info("/notice/n_delete.do");
        return "notice/delete";
    }



    //공지사항 삭제
    @PostMapping("/notice/n_deleteOK.do")
    public String deleteOK(NoticeVO vo) {
        log.info("/notice/n_deleteOK.do");
        log.info("vo:{}", vo);

        int result = service.deleteOK(vo);
        log.info("result:{}", result);
        if (result == 1) {
            return "redirect:/notice/n_selectAll.do";
        } else {
            return "redirect:/notice/n_delete.do?num=" + vo.getNum();
        }
    }



}


