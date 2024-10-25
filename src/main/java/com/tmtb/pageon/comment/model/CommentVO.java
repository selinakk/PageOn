package com.tmtb.pageon.comment.model;

import lombok.Data;

import java.util.Date;

@Data
public class CommentVO {
    private int num;            // 댓글 번호
    private String type;       // 댓글 타입 (board, forum, review)
    private Integer bnum;      // 게시글 번호
    private Integer fnum;      // 포럼 번호
    private Integer rnum;      // 리뷰 번호
    private Integer cnum;      // 대댓글 번호
    private String content;     // 댓글 내용
    private Date wdate;        // 작성일
    private String user_id;     // 작성자 ID
    private int report;        // 신고 여부
}