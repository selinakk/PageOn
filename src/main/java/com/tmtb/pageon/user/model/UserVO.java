package com.tmtb.pageon.user.model;

import lombok.Data;
import org.springframework.util.ResourceUtils;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Date;


@Data
public class UserVO {
    private String id;
    private String pw;
    private String name;
    private String tel;
    private String  like_categories;
    private String introduce;
    private String user_role;
    private String img_name; // 변수명 확인
    private Date regDate; // 이 부분이 누락되었을 수도 있습니다.
    private byte[] img_data; // BLOB 데이터를 저장하기 위한 필드
    private String email;




    public String getImgDataAsBase64() {
        if (img_data != null && img_data.length > 0) {
            return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(img_data);
        } else {
            try {
                File defaultImageFile = ResourceUtils.getFile("classpath:static/img/default.png");
                byte[] defaultImageData = Files.readAllBytes(defaultImageFile.toPath());
                return "data:image/png;base64," + Base64.getEncoder().encodeToString(defaultImageData);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }



}