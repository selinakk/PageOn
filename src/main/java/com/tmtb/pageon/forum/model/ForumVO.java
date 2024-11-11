package com.tmtb.pageon.forum.model;

import lombok.Data;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Date;
import java.util.Base64;

@Data
public class ForumVO {
    private int num;
    private int work_num;
    private String title;
    private String content;
    private String user_id;
    private String workType;
    private Date wdate;
    private boolean report;
    private int hitcount;
    private int commentCnt;

    //member 테이블 참조
    private String userName;
    private byte[] userImgData;

    //work 테이블 참조
    private String workTitle;
    private String workImgName;

    public String getImgDataAsBase64() {
        if (userImgData != null && userImgData.length > 0) {
            return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(userImgData);
        } else {
            try {
                Resource resource = new ClassPathResource("static/img/default.png");
                InputStream inputStream = resource.getInputStream();
                byte[] defaultImageData = inputStream.readAllBytes();
                return "data:image/png;base64," + Base64.getEncoder().encodeToString(defaultImageData);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}