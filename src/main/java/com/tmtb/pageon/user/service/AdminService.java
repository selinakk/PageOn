package com.tmtb.pageon.user.service;

import com.tmtb.pageon.user.mapper.AdminMapper;
import com.tmtb.pageon.user.mapper.UserMapper;
import com.tmtb.pageon.user.model.BoardVO;
import com.tmtb.pageon.user.model.ReviewVO;
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
    @Autowired
    private UserMapper userMapper;

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

    // 서비스 클래스 - 이미지가 선택되지 않았을 경우 기존 이미지를 유지
    public void updateUserInfo(UserVO user, MultipartFile imgFile) throws IOException {
        try {
            if (!imgFile.isEmpty()) {
                // 새 이미지 파일이 업로드된 경우에만 img_name과 img_data를 설정
                log.info("Uploaded file name: " + imgFile.getOriginalFilename());
                log.info("File size: " + imgFile.getSize());
                user.setImg_name(imgFile.getOriginalFilename());
                user.setImg_data(imgFile.getBytes());
                log.info("Image data size: " + user.getImg_data().length); // 데이터 크기 확인
            } else {
                // 새로운 이미지가 없는 경우 기존 이미지 정보 유지
                UserVO existingUser = userMapper.findById(user.getId());
                if (existingUser != null) {
                    user.setImg_name(existingUser.getImg_name());
                    user.setImg_data(existingUser.getImg_data());
                }
                log.info("No new image uploaded. Keeping existing image data.");
            }
            mapper.updateMember(user);
        } catch (IOException e) {
            log.error("Error while processing the image file", e);
        }
    }

    // 검색된 게시물 조회
    public List<BoardVO> getReportedBoards(int page, int size, String keyword) {
        int offset = (page - 1) * size;
        return mapper.searchReportedBoards(keyword, size, offset);
    }

    // 검색된 리뷰 조회
    public List<ReviewVO> getReportedReviews(int page, int size, String keyword) {
        int offset = (page - 1) * size;
        return mapper.searchReportedReviews(keyword, size, offset);
    }

    // 검색된 게시물의 총 페이지 수
    public int getBoardTotalPages(int size, String keyword) {
        int totalRecords = mapper.countSearchReportedBoards(keyword);
        return (int) Math.ceil((double) totalRecords / size);
    }

    // 검색된 리뷰의 총 페이지 수
    public int getReviewTotalPages(int size, String keyword) {
        int totalRecords = mapper.countSearchReportedReviews(keyword);
        return (int) Math.ceil((double) totalRecords / size);
    }

    public void deleteBoard(int num) {
        mapper.deleteBoardById(num);
    }

    public void deleteReview(int num) {
        mapper.deleteReviewById(num);
    }


}