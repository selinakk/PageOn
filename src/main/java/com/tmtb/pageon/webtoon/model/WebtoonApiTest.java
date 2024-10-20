package com.tmtb.pageon.webtoon.model;

import lombok.Data;

import java.util.List;

@Data
public class WebtoonApiTest {

    private String id;
    private String title;
    private String provider;
    private String url;
    private List<String> thumbnail; // 변경된 부분
    private String isend;
    private String isfree;
    private String isUpdated;
    private String ageGrade;
    private String freeWaitHour;
    private List<String> authors;

}
