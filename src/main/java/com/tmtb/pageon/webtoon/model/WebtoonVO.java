package com.tmtb.pageon.webtoon.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WebtoonVO {

    private Integer num;
    private String type;
    private String title;

    @JsonProperty("outline")
    private String desc;

    @JsonProperty("sntncWritrNm")
    private String writer;

    @JsonProperty("pictrWritrNm")
    private String illustrator;

    @JsonProperty("pltfomCdNm")
    private String provider;

    @JsonProperty("updateDays")
    private String update_day; // List<String>에서 String으로 변경

    private String rank;

    @JsonProperty("mainGenreCdNm")
    private String categories;

    @JsonProperty("imageDownloadUrl")
    private String img_name;

}
