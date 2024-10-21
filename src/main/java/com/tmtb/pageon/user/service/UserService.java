package com.tmtb.pageon.user.service;

import com.tmtb.pageon.user.model.BoardVO;
import com.tmtb.pageon.user.model.ForumVO;
import com.tmtb.pageon.user.model.ReviewVO;
import com.tmtb.pageon.user.model.UserVO;
import com.tmtb.pageon.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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

    public List<ForumVO> findByForum(String id) {
        return mapper.findByForum(id);
    }

    public List<BoardVO> findByBoard(String id) {
        return mapper.findByBoard(id);
    }

    public List<ReviewVO> findByReviews(String id) {
        return mapper.findByReviews(id);
    }
}
