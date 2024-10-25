package com.tmtb.pageon.user.service;

import com.tmtb.pageon.user.model.*;
import com.tmtb.pageon.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserMapper mapper;

    public void insertUser(UserVO userVO) {
        MultipartFile imgFile = userVO.getImgFile();  // userVO에서 MultipartFile을 가져옴
        String imgPath = "C:\\Project2\\src\\main\\resources\\static\\profile\\";  // 실제 저장 경로 설정
        String imgName = imgFile.getOriginalFilename(); // 원본 파일명 가져오기

        try {
            // 이미지 파일 저장
            Path path = Paths.get(imgPath, imgName);
            imgFile.transferTo(new File(path.toString()));

            // 이미지 경로와 파일명을 UserVO 객체에 설정
            userVO.setImgname(imgName);
            userVO.setImgPath(imgPath);

            // 사용자 정보 DB에 저장
            mapper.insertUser(userVO);
        } catch (IOException e) {
            e.printStackTrace();
            // 예외 처리 로직 추가 가능
        }
    }

    public boolean selectUser(String id) {
        int count = mapper.selectfindUser(id);
        return count > 0; // 중복 아이디가 존재하면 true
    }

    public UserVO findById(String id) {
        return mapper.findById(id);
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

    public void updateUserInfo(UserVO userVO) {
        MultipartFile imgFile = userVO.getImgFile();  // userVO에서 MultipartFile을 가져옴
        String imgPath = "C:\\Project2\\src\\main\\resources\\static\\profile\\";  // 실제 저장 경로 설정

        if (imgFile != null && !imgFile.isEmpty()) {  // 새로운 이미지가 업로드된 경우
            String imgName = imgFile.getOriginalFilename(); // 원본 파일명 가져오기

            try {
                // 이미지 파일 저장
                Path path = Paths.get(imgPath, imgName);
                imgFile.transferTo(new File(path.toString()));

                // 이미지 경로와 파일명을 UserVO 객체에 설정
                userVO.setImgname(imgName);
                userVO.setImgPath(imgPath);
            } catch (IOException e) {
                e.printStackTrace();
                // 예외 처리 로직 추가 가능
            }
        } else {
            // 이미지 파일이 없을 경우 기존 이미지 유지 로직
            // DB에서 기존 사용자 정보를 조회하여 imgname, imgPath를 userVO에 설정하는 코드 추가 필요
            UserVO existingUser = mapper.findById(userVO.getId()); // ID로 기존 사용자 조회
            if (existingUser != null) {
                userVO.setImgname(existingUser.getImgname()); // 기존 이미지 이름 유지
                userVO.setImgPath(existingUser.getImgPath()); // 기존 이미지 경로 유지
            }
        }

        // 사용자 정보 DB에 저장
        mapper.updateUserInfo(userVO);
    }
    public void updateUserCategories(String id, String likeCategories) {
        mapper.updateCategories(id, likeCategories);
    }





}
