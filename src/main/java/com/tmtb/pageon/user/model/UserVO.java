package com.tmtb.pageon.user.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;


@Data
public class UserVO {
    private String id;
    private String pw;
    private String name;
    private String tel;
    private String  like_categories;
    private String introduce;
    private String imgname;
    private String imgPath;
    private MultipartFile imgFile; // 파일을 받을 필드
    private Date regDate; // 이 부분이 누락되었을 수도 있습니다.
}