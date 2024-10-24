package com.tmtb.pageon.webtoon.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WebtoonVO {

    private Integer num;
    private String type;
    private String title;

    @JsonProperty("outline")
    private String desc;

    @JsonProperty("pictrWritrNm")
    private String writer;

    @JsonProperty("illustWritrNm")
    private String illustrator;

    @JsonProperty("pltfomCdNm")
    private String provider;

    private String update_day;

    private String rank;

    @JsonProperty("mainGenreCdNm")
    private String categories;

    @JsonProperty("imageDownloadUrl")
    private String img_name;

}
