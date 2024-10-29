package com.tmtb.pageon.user.service;

import com.tmtb.pageon.user.model.*;
import com.tmtb.pageon.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper mapper;

    // 사용자 등록
    public void insertUser(UserVO user, MultipartFile imgFile) {
        try {
            user.setImg_name(imgFile.getOriginalFilename());
            user.setImg_data(imgFile.getBytes());
            mapper.insertUser(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // 아이디 중복 체크
    public boolean selectUser(String id) {
        int count = mapper.selectfindUser(id);
        return count > 0; // 중복 아이디 체크
    }

    public UserVO findById(String id) {
        return mapper.findById(id);
    }
    // 사용자 정보 업데이트
    public void updateUserInfo(UserVO user, MultipartFile imgFile) {
        try {
            if (!imgFile.isEmpty()) {
                log.info("Uploaded file name: " + imgFile.getOriginalFilename());
                log.info("File size: " + imgFile.getSize());
                user.setImg_name(imgFile.getOriginalFilename());
                user.setImg_data(imgFile.getBytes());
                log.info("Image data size: " + user.getImg_data().length); // 데이터 크기 확인
            } else {
                log.info("No new image uploaded. Keeping existing image data.");
            }
            mapper.updateUserInfo(user);
        } catch (IOException e) {
            log.error("Error while processing the image file", e);
        }
    }

    // 카테고리 업데이트
    public void updateUserCategories(String id, String likeCategories) {
        mapper.updateCategories(id, likeCategories);
    }




    public List<ForumVO> findByForumPazing(String id, int offset, int size) {
        return mapper.findByforumPazing(id, offset, size);
    }

    public List<BoardVO> findByBoardPazing(String id, int offset, int size) {
        return mapper.findBoardsByUserPazing(id, offset, size);
    }

    public List<ReviewVO> findByReviewsPazing(String id, int offset, int size) {
        return mapper.findByReviewsPazing(id, offset, size);
    }

    public List<CommentVO> findCommentsByUserPazing(String id, int offset, int size) {
        return mapper.findCommentsByUserPazing(id, offset, size);
    }


    // 기타 조회 메서드...
    public List<ForumVO> findByForum(String id) {
        return mapper.findByForum(id);
    }

    public List<BoardVO> findByBoard(String id) {
        return mapper.findByBoard(id);
    }

    public List<ReviewVO> findByReviews(String id) {
        return mapper.findByReviews(id);
    }

    public List<CommentVO> findByComment(String id) {
        return mapper.findByComment(id);
    }
}
