package com.tmtb.pageon.user.service;

import com.tmtb.pageon.user.model.*;
import com.tmtb.pageon.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper mapper;

    @Autowired
    private PasswordEncoder encoder;
    // 사용자 등록
    public void insertUser(UserVO user, MultipartFile imgFile) {
        try {
            // 이미지 파일 처리 (InputStream 및 바이트 배열로 처리)
            if (imgFile != null && !imgFile.isEmpty()) {
                user.setImg_name(imgFile.getOriginalFilename());  // 파일 이름 저장

                // InputStream으로 변환하여 이미지 데이터를 바이트 배열로 읽기
                InputStream inputStream = imgFile.getInputStream();
                byte[] imgData = inputStream.readAllBytes();
                user.setImg_data(imgData);  // 이미지 데이터 저장
            }

            // 비밀번호 암호화
            String encodedPwd = encoder.encode(user.getPw());
            user.setPw(encodedPwd);

            log.info("userVO 정보: {}", user);  // 사용자 정보 로그로 출력

            // 사용자 등록
            mapper.insertUser(user);  // 매퍼를 통해 DB에 사용자 정보 저장
        } catch (IOException e) {
            e.printStackTrace();
            log.error("파일 처리 중 오류 발생: {}", e.getMessage());
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
                // 새 이미지가 업로드된 경우 이미지 데이터 설정
                log.info("Uploaded file name: " + imgFile.getOriginalFilename());
                log.info("File size: " + imgFile.getSize());

                // InputStream을 사용하여 이미지 데이터 바이트 배열로 읽기
                InputStream inputStream = imgFile.getInputStream();
                byte[] imgData = inputStream.readAllBytes();
                user.setImg_name(imgFile.getOriginalFilename()); // 파일 이름 저장
                user.setImg_data(imgData); // 이미지 데이터 바이트 배열 저장

            } else {
                // 이미지가 업로드되지 않은 경우 기존 이미지 정보를 유지
                UserVO existingUser = mapper.findById(user.getId()); // 기존 사용자 정보 가져오기
                user.setImg_name(existingUser.getImg_name()); // 기존 이미지 이름 유지
                user.setImg_data(existingUser.getImg_data()); // 기존 이미지 데이터 유지
                log.info("No new image uploaded. Keeping existing image data.");
            }

            // 사용자 정보 업데이트
            mapper.updateUserInfo(user);
        } catch (IOException e) {
            log.error("Error while processing the image file", e);
        }
    }

    // 카테고리 업데이트
    public void updateUserCategories(String id, String likeCategories) {
        mapper.updateCategories(id, likeCategories);
    }





    public List<ForumVO> findByForumPazing(String id, int offset, int size, String searchKeyword) {
        return mapper.findByforumPazing(id,offset,size,searchKeyword); // searchKeyword 추가
    }


    public List<BoardVO> findByBoardPazing(String id, int offset, int size, String searchKeyword) {
        return mapper.findBoardsByUserPazing(id, offset, size, searchKeyword);  // searchKeyword 추가
    }


    public List<ReviewVO> findByReviewsPazing(String id, int offset, int size, String searchKeyword) {
        return mapper.findByReviewsPazing(id, offset, size, searchKeyword);  // searchKeyword 추가
    }


    public List<CommentVO> findCommentsByUserPazing(String id, int offset, int size, String searchKeyword) {
        return mapper.findCommentsByUserPazing(id, offset, size, searchKeyword);  // searchKeyword 추가
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



    // 비밀번호 변경 메서드
    public void updatePassword(String id, String pw, String email) {
        String encodedPwd = encoder.encode(pw); // 새로운 비밀번호 암호화
        mapper.updatePassword(id, encodedPwd,email); // 데이터베이스에 업데이트
    }

    public boolean selectfindPw(String id, String email){
        int count = mapper.selectupdatePassword(id, email);
        return count > 0;

    }


    public String findUserIdByEmail(String email) {
        return mapper.findUserIdByEmail(email);
    }

    // 아이디 중복 체크
    public boolean selectfindEmail(String email) {
        int count = mapper.selectfindEmail(email);
        return count > 0; // 중복 아이디 체크
    }

    public  int countCommentsByUser(String id,String searchKeyword){
        return mapper.countCommentsByUser(id,searchKeyword);
    }

    public  int countForumsByUser(String id,String searchKeyword){
        return mapper.countForumsByUser(id,searchKeyword);
    }

    public  int countReviewsByUser(String id,String searchKeyword){
        return mapper.countReviewsByUser(id,searchKeyword);
    }

    public  int countBoardsByUser(String id,String searchKeyword){
        return mapper.countBoardsByUser(id,searchKeyword);
    }


}