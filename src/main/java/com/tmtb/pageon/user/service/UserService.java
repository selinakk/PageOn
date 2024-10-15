package com.tmtb.pageon.user.service;


import com.tmtb.pageon.user.model.UserVO;
import com.tmtb.pageon.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UserService {

    @Autowired
    UserMapper mapper;


    public void insertMember(UserVO userVO) {
        // 이미지 저장 경로 및 파일명 설정
        MultipartFile imgFile = userVO.getImgFile();  // userVO에서 MultipartFile을 가져옴
        String imgPath = "C:\\Project2\\src\\main\\webapp\\USER-Image\\";       // 실제 저장 경로 설정
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
}
