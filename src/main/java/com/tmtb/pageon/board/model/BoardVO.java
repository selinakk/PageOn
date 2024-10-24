package com.tmtb.pageon.board.model;

import lombok.Data;

import java.sql.Date;

@Data
public class BoardVO {

    private int num;
    private String title;
    private String content;
    private String user_id;
    private Date wdate;
    private String image_name;
    private boolean report;
    private int category;
    private int hitcount;

}
