package com.tmtb.pageon.webtoon.model;

import lombok.Data;

import java.util.List;

@Data
public class WebtoonApiTest {

    private String id;
    private String title;
    private String provider;
    private String url;
    private List<String> thumbnail;
    private String isend;
    private String isfree;
    private String isUpdated;
    private String ageGrade;
    private String freeWaitHour;
    private List<String> authors;
    private String type;

}
