package com.tmtb.pageon.user.model;

import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
public class ForumVO {
    private int num;
    private int work_num;
    private String title;
    private String content;
    private String user_id;
    private Date wdate;
    private boolean report;
    private int hitcount;
    private int comment_count;

    // member 테이블 참조
    private String userName;
    private String userImgName;

    // work 테이블 참조
    private String workTitle;
    private String workImgName;
}