package com.tmtb.pageon.notice.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@Data
public class NoticeVO {

    private int num;
    private String user_id;
    private String title;
    private String content;
    private Timestamp wdate;
    private String img_name;
    private MultipartFile file;
    private int hitcount;


}