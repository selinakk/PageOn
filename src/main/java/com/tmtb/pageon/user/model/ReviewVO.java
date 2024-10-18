package com.tmtb.pageon.user.model;

import lombok.Data;

import java.util.Date;


@Data
public class ReviewVO {
    private int num;

    private int inum;

    private String title;

    private String content;

    private String user_id;

    private Date wdate;

    private Boolean report;

    private int likes;

}
