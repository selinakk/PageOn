package com.tmtb.pageon.user.model;

import lombok.Data;

import java.util.Date;

@Data
public class BoardVO {

    private int num;
    private String title;
    private String content;
    private String userId;
    private Date wdate;
    private String imgName;
    private int report;
    private String category;
    private int hitcount;
}
