package com.tmtb.pageon.board.controller;

import com.tmtb.pageon.board.service.BoardService;
import com.tmtb.pageon.board.model.BoardVO;
import com.tmtb.pageon.comment.controller.CommentController;
import com.tmtb.pageon.comment.model.CommentVO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private CommentController commentController;

    @Autowired
    ServletContext context;

    @Value("${file.dir}")
    private String uploadDir;

    //자유게시판 목록
    @GetMapping("/freeboard")
    public String freeboard(@RequestParam(defaultValue = "1") int page, Model model, String sort) {
        log.info("자유게시판 페이지");

        int pageSize = 20;
        int totalCount = boardService.getTotalCountFreeBoard();
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        List<BoardVO> boardList;
        //자유게시판,질문게시판 조회수 카운트
        if ("hitcount".equals(sort)) {
            boardList = boardService.getFreeBoardListByHitCount(page, pageSize);
        } else {
            boardList = boardService.getFreeBoardList(page, pageSize);
        }

        model.addAttribute("category", "free");
        model.addAttribute("boardList", boardList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("sort", sort);
        return "board/freeboard";
    }

    //질문 게시판 목록
    @GetMapping("/qnaboard")
    public String qnaboard(@RequestParam(defaultValue = "1") int page, Model model, String sort) {
        log.info("QnA게시판 페이지");

        int pageSize = 20;
        int totalCount = boardService.getTotalCountQnaBoard();
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        List<BoardVO> boardList;
        if ("hitcount".equals(sort)) {
            boardList = boardService.getQnaBoardListByHitCount(page, pageSize);
        } else {
            boardList = boardService.getQnaBoardList(page, pageSize);
        }

        model.addAttribute("category", "qna");
        model.addAttribute("boardList", boardList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("sort", sort);
        return "board/qnaboard";
    }

    //게시판 글 작성 페이지
    @GetMapping("/b_insert")
    public String b_insert(Model model, HttpSession session) {
        log.info("게시글 작성 페이지");

        String userId = (String) session.getAttribute("id");
        log.info("세션에서 가져온 사용자 ID: {}", userId);

        model.addAttribute("userId", userId);


        return "board/insert";
    }

    //게시판 글 작성 완료
    @PostMapping("/b_insertOK")
    public String b_insertOK(BoardVO vo) throws IOException {
        log.info("게시글 작성 완료");


        // 상대 경로를 절대 경로로 변환
        String realPath = Paths.get(uploadDir).toAbsolutePath().toString();

        File uploadDir = new File(realPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String originName = vo.getFile().getOriginalFilename();
        log.info("originName:{}", originName);

        if (originName.length() == 0) {
            vo.setImg_name("");
        } else {
            String save_name = "img_" + System.currentTimeMillis() + originName.substring(originName.lastIndexOf("."));
            log.info("save_name:{}", save_name);
            vo.setImg_name(save_name);

            File f = new File(realPath, save_name);
            vo.getFile().transferTo(f);

            BufferedImage original_buffer_img = ImageIO.read(f);
            BufferedImage thumb_buffer_img = new BufferedImage(50, 50, BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D graphic = thumb_buffer_img.createGraphics();
            graphic.drawImage(original_buffer_img, 0, 0, 50, 50, null);

            File thumb_file = new File(realPath, "thumb_" + save_name);

            ImageIO.write(thumb_buffer_img, save_name.substring(save_name.lastIndexOf(".") + 1), thumb_file);
        }

        boardService.insertOK(vo);

        if ("qna".equals(vo.getCategory())) {
            return "redirect:/qnaboard";
        } else {
            return "redirect:/freeboard";
        }
    }

    //게시글 상세 보기
    @GetMapping("/b_selectOne")
    public String b_selectOne(BoardVO vo, @AuthenticationPrincipal UserDetails userDetails, Model model, HttpSession session, @RequestParam(defaultValue = "free") String category, @RequestParam(defaultValue = "1") int cpage,
                              @RequestParam(defaultValue = "20") int pageBlock) {
        // 세션에서 사용자 ID 가져오기
        String id = (String) session.getAttribute("id");
        log.info("세션에서 가져온 사용자 ID: {}", id);

        log.info("게시글 상세보기 페이지");



        boardService.updateBoardHitCount(vo);

        BoardVO vo2 = boardService.selectOne(vo);
        log.info("vo2:{}", vo2);


        model.addAttribute("vo2", vo2);
        model.addAttribute("category", category);

        if (userDetails != null) {
            model.addAttribute("currentUserId", userDetails.getUsername());
        } else {
            model.addAttribute("currentUserId", null);
        }


        // 댓글 데이터 가져오기
        Map<String, Object> commentsData = commentController.selectAll("board", vo.getNum(), null, null, 1, 20);
        List<CommentVO> comments = (List<CommentVO>) commentsData.get("comments");
        int totalPageCount = (int) commentsData.get("totalPageCount");
        // 전체 댓글 수 가져오기
        int totalRows = (int) commentsData.get("totalRows");


        // 댓글 데이터를 모델에 추가
        model.addAttribute("comments", comments);
        // 총 페이지 수 계산 후 모델에 추가
        model.addAttribute("totalPageCount", (int) Math.ceil((double) totalRows / pageBlock));
        // cpage와 pageBlock을 모델에 추가
        model.addAttribute("cpage", cpage);
        model.addAttribute("pageBlock", pageBlock);


        return "board/selectOne";
    }

    //게시글 삭제 완료
    @PostMapping("/b_deleteOK")
    public String b_deleteOK(BoardVO vo) {
        log.info("삭제완료 페이지");

        int result = boardService.deleteOK(vo);
        log.info("result:{}", result);
        if (result == 1) {
            if ("qna".equals(vo.getCategory())) {
                return "redirect:/qnaboard";
            } else {
                return "redirect:/freeboard";
            }
        } else {
            return "redirect:/b_delete?num=" + vo.getNum();
        }
    }

    //게시글 수정 페이지
    @GetMapping("/b_update")
    public String b_update(BoardVO vo, Model model) {
        log.info("수정페이지입니다.");
        BoardVO vo2 = boardService.selectOne(vo);
        model.addAttribute("vo2", vo2);
        return "board/update";
    }

    //게시글 수정 완료
    @PostMapping("/b_updateOK")
    public String b_updateOK(@RequestParam("file") MultipartFile file,
                             @RequestParam("existingFile") String existingFile,
                             BoardVO vo) throws IOException {
        log.info("수정완료 페이지");

        // 상대 경로를 절대 경로로 변환
        String realPath = Paths.get(uploadDir).toAbsolutePath().toString();

        File uploadDir = new File(realPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        if (!file.isEmpty()) {
            String originName = file.getOriginalFilename();
            log.info("originName:{}", originName);

            String save_name = "img_" + System.currentTimeMillis() + originName.substring(originName.lastIndexOf("."));
            log.info("save_name:{}", save_name);
            vo.setImg_name(save_name);

            File f = new File(realPath, save_name);
            file.transferTo(f);

            BufferedImage original_buffer_img = ImageIO.read(f);
            BufferedImage thumb_buffer_img = new BufferedImage(50, 50, BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D graphic = thumb_buffer_img.createGraphics();
            graphic.drawImage(original_buffer_img, 0, 0, 50, 50, null);

            File thumb_file = new File(realPath, "thumb_" + save_name);

            ImageIO.write(thumb_buffer_img, save_name.substring(save_name.lastIndexOf(".") + 1), thumb_file);
        } else {
            vo.setImg_name(existingFile);
        }

        int result = boardService.updateBoard(vo);
        log.info("result:{}", result);
        if (result == 1) {
            if ("qna".equals(vo.getCategory())) {
                return "redirect:/qnaboard";
            } else {
                return "redirect:/freeboard";
            }
        } else {
            return "redirect:/b_update?num=" + vo.getNum();
        }
    }


    //게시판 신고 기능
    @PostMapping("/b_reportOK")
    @ResponseBody
    public ResponseEntity<String> b_reportOK(BoardVO vo) {
        log.info("신고완료 ");
        boardService.updateReport(vo);
        return new ResponseEntity<>("신고되었습니다", HttpStatus.OK); // "신고되었습니다" 메시지와 함께 HTTP 200 상태 코드를 반환
    }


    //게시글 검색
    @GetMapping("/b_search")
    public String searchBoard(@RequestParam(required = true) String searchWord,
                              @RequestParam String searchType,
                              @RequestParam String category,
                              @RequestParam(required = false) String sort,
                              @RequestParam(defaultValue = "1") int page,
                              Model model) {
        log.info("게시판 검색");

        int pageSize = 20;
        int offset = (page - 1) * pageSize;

        List<BoardVO> boardList;
        int totalCount;
        if ("title".equals(searchType)) {
            boardList = boardService.searchBoardByTitle(searchWord, category, offset, pageSize);
            totalCount = boardService.getTotalCountByTitle(searchWord, category);
        } else {
            boardList = boardService.searchBoardByContent(searchWord, category, offset, pageSize);
            totalCount = boardService.getTotalCountByContent(searchWord, category);
        }

        log.info("searchType:{}", searchType);
        log.info("searchWord:{}", searchWord);

        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        model.addAttribute("boardList", boardList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("searchType", searchType);
        model.addAttribute("category", category);
        model.addAttribute("sort", sort);

        if ("qna".equals(category)) {
            return "board/qnaboard";
        } else {
            return "board/freeboard";
        }
    }


}
