package com.tmtb.pageon.review.model;

import lombok.Data;

@Data
public class ReviewVO {
    private int num;
    private int work_num;
    private String title;
    private String content;
    private String user_id;
    private String wdate;
    private double rating;
    private boolean report;
    private int like;
    private int hate;
}
