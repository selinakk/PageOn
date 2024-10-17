package com.tmtb.pageon.bookshelf.model;

import lombok.Data;

import java.sql.Date;

@Data
public class BookshelfVO {
    private int num;
    private String user_id;
    private String sort;
    private int work_num;
    private Date date_added;

    private String name; //빼도 될듯?
    private String work_title;
    private String img_name;
}
