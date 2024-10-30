package com.tmtb.pageon.webtoon.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class WebtoonVO {

    private Integer item_id; // 웹툰 고유번호
    private String type; // 웹툰, 소설, 웹소설
    private String title; // 제목

    @JsonProperty("outline")
    private String desc; // 줄거리

    @JsonProperty("sntncWritrNm")
    private String writer; // 작가

    @JsonProperty("pictrWritrNm")
    private String illustrator; // 그림작가

    @JsonProperty("pltfomCdNm")
    private String provider; // 제공자

    @JsonProperty("updateDays")
    private String update_day; //올라오는 요일

    private String rank; //별점

    @JsonProperty("mainGenreCdNm")
    private String categories; //장르

    @JsonProperty("imageDownloadUrl")
    private String img_name; //이미지

    @JsonProperty("url")
    private String link; //링크

    private Integer added_bs; //등록일




    //해석 부분 줄바꿈 적용
    public String getFormattedDesc() {
        return this.desc != null ? this.desc.replace("\r\n", "<br>").replace("\n", "<br>") : null;
    }

    //요일 변환용
    private static final Map<String, String> dayMap = new HashMap<>();
    static {
        dayMap.put("MON", "월요일");
        dayMap.put("TUE", "화요일");
        dayMap.put("WED", "수요일");
        dayMap.put("THU", "목요일");
        dayMap.put("FRI", "금요일");
        dayMap.put("SAT", "토요일");
        dayMap.put("SUN", "일요일");
    }
    //요일 변환 함수
    public String getFormattedUpdateDay() {
        return dayMap.getOrDefault(this.update_day, this.update_day);
    }

}
