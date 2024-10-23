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

    //member 테이블 참조
    private String userName;

    //work 테이블 참조
    private String workTitle;
    private String workImgName;
}
