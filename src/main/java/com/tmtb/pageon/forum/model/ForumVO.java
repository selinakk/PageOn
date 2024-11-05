package com.tmtb.pageon.forum.model;

import lombok.Data;

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
        if (userImgData != null) {
            return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(userImgData);
        }
        return null;
    }
}