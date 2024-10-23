package com.tmtb.pageon.Book.model;

import lombok.Data;

@Data
public class BookVO {
    private int item_id;
    private String type;
    private String title;
    private String desc;       // 테이블의 desc 컬럼과 매칭
    private String writer;     // 테이블의 writer 컬럼과 매칭
    private String provider;   // 테이블의 provider 컬럼과 매칭
    private String categories;
    private String img_name;
    private String link;  // 상품 링크 추가
}
