package com.tmtb.pageon.forum.model;

import lombok.Data;

import java.sql.Date;

@Data
public class ForumVO {
    private int num;
    private int work_num;
    private String title;
    private String content;
    private String user_id;
    private Date wdate;
    private boolean report;
    private int hitcount;
    private int commentCnt;

    //member 테이블 참조
    private String userName;
    private String userImgName;

    //work 테이블 참조
    private String workTitle;
    private String workImgName;

    //네이밍 규칙
    //외부 테이블의 중복 필드명은 카멜케이스로 변경
    //외부 테이블의 포괄적인 또는 불특정 필드명은 컨트롤러에서 변수명 카멜케이스로 받기
    //기타 매개변수 카멜케이스로 변경
}