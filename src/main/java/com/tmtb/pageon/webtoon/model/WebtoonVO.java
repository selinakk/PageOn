package com.tmtb.pageon.webtoon.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WebtoonVO {

    private Integer item_id;
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
    private String update_day;

    private String rank;

    @JsonProperty("mainGenreCdNm")
    private String categories;

    @JsonProperty("imageDownloadUrl")
    private String img_name;




    //해석 부분 줄바꿈 적용
    public String getFormattedDesc() {
        return this.desc != null ? this.desc.replace("\r\n", "<br>").replace("\n", "<br>") : null;
    }

}
