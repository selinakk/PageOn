package com.tmtb.pageon.user.model;

import lombok.Data;

import java.util.Date;

@Data
public class BoardVO {

    private int num;
    private String title;
    private String content;
    private String user_id;
    private Date wdate;
    private String img_name;
    private int report;
    private String category;
    private int boardhitcount;
}