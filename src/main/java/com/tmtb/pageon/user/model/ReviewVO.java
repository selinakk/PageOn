package com.tmtb.pageon.user.model;

import lombok.Data;

import java.util.Date;


@Data
public class ReviewVO { private int num;
    private int work_num; // 추가된 필드
    private String title;
    private String content;
    private String user_id;
    private Date wdate;
    private double rating; // 추가된 필드 (평점)
    private Boolean report; // 신고
    private int hate; // 추가된 필드
    private int like; // 추가된 필드
    private String name;

}