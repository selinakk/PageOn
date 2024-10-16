package com.tmtb.pageon.board.controller;

import com.tmtb.pageon.board.service.BoardService;
import com.tmtb.pageon.board.model.BoardVO;
import jakarta.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    ServletContext context;

    @GetMapping("/freeboard")
    public String freeboard(@RequestParam(defaultValue = "1") int page, Model model, String sort) {
        log.info("자유게시판 페이지");

        int pageSize = 15;
        int totalCount = boardService.getTotalCount();
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        List<BoardVO> boardList;
        if ("hitcount".equals(sort)) {
            boardList = boardService.getFreeBoardListByHitCount(page, pageSize);
        } else {
            boardList = boardService.getFreeBoardList(page, pageSize);
        }

        model.addAttribute("boardList", boardList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("sort", sort);
        return "board/freeboard";
    }

    @GetMapping("/qnaboard")
    public String qnaboard(@RequestParam(defaultValue = "1") int page, Model model, String sort) {
        log.info("QnA게시판 페이지");

        int pageSize = 15;
        int totalCount = boardService.getTotalCount();
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        List<BoardVO> boardList;
        if ("hitcount".equals(sort)) {
            boardList = boardService.getQnaBoardListByHitCount(page, pageSize);
        } else {
            boardList = boardService.getQnaBoardList(page, pageSize);
        }


        model.addAttribute("boardList", boardList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("sort", sort);
        return "board/qnaboard";
    }


    @GetMapping("/b_insert")
    public String b_insert(Model model) {
        log.info("게시글 작성 페이지");

        return "board/insert";
    }

    @PostMapping("/b_insertOK")
    public String b_insertOK(BoardVO vo) throws IOException {
        log.info("게시글 작성 완료");


        String realPath = context.getRealPath("resources/upload_img");
        log.info(realPath);

        File uploadDir = new File(realPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String originName = vo.getFile().getOriginalFilename();
        log.info("originName:{}", originName);

        if (originName.length() == 0) {
            vo.setImg_name("default.png");
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

    @GetMapping("/b_selectOne")
    public String b_selectOne(BoardVO vo, Model model) {
        log.info("게시글 상세보기 페이지");

        boardService.updateBoardHitCount(vo);

        BoardVO vo2 = boardService.selectOne(vo);
        log.info("vo2:{}", vo2);

        model.addAttribute("vo2", vo2);

        return "board/selectOne";
    }

    @GetMapping("/b_delete")
    public String b_delete() {
        log.info("삭제페이지입니다.");
        return "board/delete";
    }

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

    @GetMapping("/b_update")
    public String b_update(BoardVO vo, Model model) {
        log.info("수정페이지입니다.");
        BoardVO vo2 = boardService.selectOne(vo);
        model.addAttribute("vo2", vo2);
        return "board/update";
    }

    @PostMapping("/b_updateOK")
    public String b_updateOK(BoardVO vo) throws IOException {
        log.info("수정완료 페이지");

        String realPath = context.getRealPath("resources/upload_img");
        log.info(realPath);

        File uploadDir = new File(realPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String originName = vo.getFile().getOriginalFilename();
        log.info("originName:{}", originName);

        if (originName.length() == 0) {
            vo.setImg_name("default.png");
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

    @GetMapping("/b_search")
    public String searchBoard(@RequestParam(required = true) String searchWord,
                              @RequestParam String searchType,
                              @RequestParam String category,
                              @RequestParam(defaultValue = "1") int page,
                              Model model) {
        log.info("게시판 검색");

        int pageSize = 15;
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

        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        model.addAttribute("boardList", boardList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("searchType", searchType);
        model.addAttribute("category", category);

        if ("qna".equals(category)) {
            return "board/qnaboard";
        } else {
            return "board/freeboard";
        }
    }


}
