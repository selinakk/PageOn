package com.tmtb.pageon.review.model;

import lombok.Data;

import java.sql.Date;


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

    private  String userName;
    private  String userImg;
}
