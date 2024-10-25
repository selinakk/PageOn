package com.tmtb.pageon.notice.controller;

import com.tmtb.pageon.forum.model.ForumVO;
import com.tmtb.pageon.forum.service.ForumService;
import com.tmtb.pageon.notice.model.NoticeVO;
import com.tmtb.pageon.notice.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class NoticeController {

    @Autowired
    NoticeService service;

    @Value("${file.dir}")
    private String realPath;



    @GetMapping("/notice/n_selectAll.do")
    public String selectAll(Model model, @RequestParam(defaultValue = "1") int cpage,
                            @RequestParam(defaultValue = "15") int pageBlock) {
        log.info("/notice/n_selectAll.do");
        log.info("cpage:{}", cpage);
        log.info("pageBlock:{}", pageBlock);

        List<NoticeVO> list = service.selectAllPageBlock(cpage, pageBlock);
        log.info("list.size():{}", list.size());

        model.addAttribute("list", list);

        int total_rows = service.getTotalRows();// select count(*) total_rows from member;
        log.info("total_rows:{}", total_rows);

        int totalPageCount = 0;

        if (total_rows / pageBlock == 0) {
            totalPageCount = 1;
        } else if (total_rows % pageBlock == 0) {
            totalPageCount = total_rows / pageBlock;
        } else {
            totalPageCount = total_rows / pageBlock + 1;
            ;
        }
        log.info("totalPageCount:{}", totalPageCount);

        model.addAttribute("totalPageCount", totalPageCount);

        return "notice/selectAll";
    }



    //오래된순
    @GetMapping("/notice/n_selectAllNew.do")
    public String selectAllNew(Model model, @RequestParam(defaultValue = "1") int cpage,
                            @RequestParam(defaultValue = "15") int pageBlock) {
        log.info("/notice/n_selectAllNew.do");
        log.info("cpage:{}", cpage);
        log.info("pageBlock:{}", pageBlock);

        List<NoticeVO> list = service.selectAllNewPageBlock(cpage, pageBlock);
        log.info("list.size():{}", list.size());

        model.addAttribute("list", list);

        int total_rows = service.getTotalRows();
        log.info("total_rows:{}", total_rows);

        int totalPageCount = 0;


        if (total_rows / pageBlock == 0) {
            totalPageCount = 1;
        } else if (total_rows % pageBlock == 0) {
            totalPageCount = total_rows / pageBlock;
        } else {
            totalPageCount = total_rows / pageBlock + 1;
            ;
        }
        log.info("totalPageCount:{}", totalPageCount);

        model.addAttribute("totalPageCount", totalPageCount);

        return "notice/selectAll";
    }



    //조회순
    @GetMapping("/notice/n_selectAllHitcount.do")
    public String selectAllHitcount(Model model, @RequestParam(defaultValue = "1") int cpage,
                               @RequestParam(defaultValue = "15") int pageBlock) {
        log.info("/notice/n_selectAllHitcount.do");
        log.info("cpage:{}", cpage);
        log.info("pageBlock:{}", pageBlock);

        List<NoticeVO> list = service.selectAllHitcountPageBlock(cpage, pageBlock);
        log.info("list.size():{}", list.size());

        model.addAttribute("list", list);

        int total_rows = service.getTotalRows();
        log.info("total_rows:{}", total_rows);

        int totalPageCount = 0;

        if (total_rows / pageBlock == 0) {
            totalPageCount = 1;
        } else if (total_rows % pageBlock == 0) {
            totalPageCount = total_rows / pageBlock;
        } else {
            totalPageCount = total_rows / pageBlock + 1;
            ;
        }
        log.info("totalPageCount:{}", totalPageCount);

        model.addAttribute("totalPageCount", totalPageCount);

        return "notice/selectAll";
    }



    @GetMapping("/notice/n_insert.do")
    public String insert() {
        log.info("/notice/n_insert.do");
        return "notice/insert";
    }



    @PostMapping("/notice/n_insertOK.do")
    public String insertOK(NoticeVO vo) throws IllegalStateException, IOException {
        log.info("/notice/n_insertOK.do");
        log.info("vo:{}", vo);

        log.info(realPath);

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

            // create thumbnail image//
            BufferedImage original_buffer_img = ImageIO.read(f);
            BufferedImage thumb_buffer_img = new BufferedImage(50, 50, BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D graphic = thumb_buffer_img.createGraphics();
            graphic.drawImage(original_buffer_img, 0, 0, 50, 50, null);

            File thumb_file = new File(realPath, "thumb_" + save_name);

            ImageIO.write(thumb_buffer_img, save_name.substring(save_name.lastIndexOf(".") + 1), thumb_file);

        }

        int result = service.insertOK(vo);
        log.info("result:{}", result);
        if (result == 1) {
            return "redirect:/notice/n_selectAll.do";
        } else {
            return "redirect:/notice/n_insert.do";
        }
    }



    @GetMapping("/notice/n_selectOne.do")
    public String selectOne(NoticeVO vo, Model model) {
        log.info("/notice/n_selectOne.do");
        log.info("vo:{}", vo);

        NoticeVO vo2 = service.selectOne(vo);
        log.info("vo2:{}", vo2);

        model.addAttribute("vo2", vo2);

        return "notice/selectOne";
    }



    @GetMapping("/notice/n_update.do")
    public String update(NoticeVO vo, Model model) {
        log.info("/notice/n_update.do");
        log.info("vo:{}", vo);

        NoticeVO vo2 = service.selectOne(vo);
        log.info("vo2:{}", vo2);

        model.addAttribute("vo2", vo2);

        return "notice/update";
    }



    @PostMapping("/notice/n_updateOK.do")
    public String updateOK(NoticeVO vo) throws IllegalStateException, IOException {
        log.info("/notice/n_updateOK.do");
        log.info("vo:{}", vo);

        log.info(realPath);

        String originName = vo.getFile().getOriginalFilename();
        log.info("originName:{}", originName);

        if (originName.length() == 0) {
            vo.setImg_name(vo.getImg_name());
        } else {
            String save_name = "img_" + System.currentTimeMillis() + originName.substring(originName.lastIndexOf("."));
            log.info("save_name:{}", save_name);
            vo.setImg_name(save_name);

            File f = new File(realPath, save_name);
            vo.getFile().transferTo(f);

            // create thumbnail image//
            BufferedImage original_buffer_img = ImageIO.read(f);
            BufferedImage thumb_buffer_img = new BufferedImage(50, 50, BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D graphic = thumb_buffer_img.createGraphics();
            graphic.drawImage(original_buffer_img, 0, 0, 50, 50, null);

            File thumb_file = new File(realPath, "thumb_" + save_name);

            ImageIO.write(thumb_buffer_img, save_name.substring(save_name.lastIndexOf(".") + 1), thumb_file);
        }

        int result = service.updateOK(vo);
        log.info("result:{}", result);
        if (result == 1) {
            return "redirect:/notice/n_selectOne.do?num=" + vo.getNum();
        } else {
            return "redirect:/notice/n_update.do?num=" + vo.getNum();
        }
    }



    @GetMapping("/notice/n_delete.do")
    public String delete() {
        log.info("/notice/n_delete.do");
        return "notice/delete";
    }



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



    @GetMapping("/notice/n_searchList.do")
    public String searchList(Model model, @RequestParam(defaultValue = "id") String searchKey,
                             @RequestParam(defaultValue = "ad") String searchWord,
                             @RequestParam(defaultValue = "1") int cpage,
                             @RequestParam(defaultValue = "15") int pageBlock) {
        log.info("/notice/n_searchList.do");
        log.info("searchKey:{}", searchKey);
        log.info("searchWord:{}", searchWord);
        log.info("cpage:{}", cpage);
        log.info("pageBlock:{}", pageBlock);

        List<NoticeVO> list = service.searchListPageBlock(searchKey, searchWord,cpage,pageBlock);
        log.info("list.size():{}", list.size());

        model.addAttribute("list", list);

        int total_rows = service.getSearchTotalRows(searchKey, searchWord);
        log.info("total_rows:{}", total_rows);
        int totalPageCount = 0;
        if (total_rows / pageBlock == 0) {
            totalPageCount = 1;
        } else if (total_rows % pageBlock == 0) {
            totalPageCount = total_rows / pageBlock;
        } else {
            totalPageCount = total_rows / pageBlock + 1;
        }
        log.info("totalPageCount:{}", totalPageCount);

        model.addAttribute("totalPageCount", totalPageCount);

        return "notice/selectAll";
    }
}


