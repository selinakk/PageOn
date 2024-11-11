package com.tmtb.pageon.review.model;

import lombok.Data;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.Base64;


@Data
public class ReviewVO {
    private int num;
    private int work_num;
    private String title;
    private String content;
    private String user_id;
    private Date wdate;
    private double rating;
    private boolean report;
    private int like;
    private int hate;
    private String categories;

    //작품이미지
    private String workTitle;
    private String workImg;

    private String userName;
    private byte[] userImgData;


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
