package com.tmtb.pageon.forum.model;

import lombok.Data;

import java.sql.Date;

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
    //work 테이블 참조
    private String work_title;
    private String img_name;
}
