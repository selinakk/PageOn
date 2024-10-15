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
    private int like;
}
