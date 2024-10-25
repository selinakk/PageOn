package com.tmtb.pageon.review.model;

import lombok.Data;

import java.sql.Date;

@Data
public class ReviewVO {

    private int num;
    private int inum;
    private String title;
    private String content;
    private String user_id;
    private Date wdate;
    private Double rating;
    private boolean report;
    private int hate;
    private int like;

}
