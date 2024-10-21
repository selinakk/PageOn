package com.tmtb.pageon.user.model;

import lombok.Data;

import java.util.Date;

@Data
public class CommentVO {
    private int num;          // 댓글 번호
    private String type;      // 댓글 유형
    private int bnum;        // 게시글 번호 (부모)
    private int fnum;        // 댓글의 부모 댓글 번호 (대댓글)
    private int rnum;        // 댓글의 답글 번호
    private int cnum;        // 댓글 번호
    private String content;   // 댓글 내용
    private Date wdate;       // 작성 날짜
    private String user_id;   // 사용자 ID
    private Boolean report;    // 신고 여부
}