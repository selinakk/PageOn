package com.tmtb.pageon.user.service;

import com.tmtb.pageon.user.mapper.AdminMapper;
import com.tmtb.pageon.user.model.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class AdminService {

    @Autowired
    private AdminMapper mapper;

    public void insertUser(UserVO userVO) {
        mapper.insertMember(userVO);
    }


    public List<UserVO> selectAllMembers(int offset, int size) {
        return mapper.selectAllMembers(offset, size);
    }

    public int countAllMembers() {
        return mapper.countAllMembers();
    }

    public void deleteUser(String id) {
        mapper.deleteMember(id);
    }

    public List<UserVO> searchMembers(String keyword, String sortOrder, int offset, int size) {
        return mapper.searchMembers(keyword, sortOrder, offset, size);
    }

    public int countSearchMembers(String keyword) {
        return mapper.countSearchMembers(keyword);
    }

    public void updateUserInfo(UserVO user, MultipartFile imgFile) throws IOException {
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
            mapper.updateMember(user);
        } catch (IOException e) {
            log.error("Error while processing the image file", e);
        }
    }
}