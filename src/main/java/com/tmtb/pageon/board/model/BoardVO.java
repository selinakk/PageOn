package com.tmtb.pageon.board.model;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class BoardVO {

    private Integer num;
    private String title;
    private String content;
    private String user_id;
    private Timestamp wdate;
    private String img_name;
    private Integer report;
    private String category;
    private Integer boardhitcount;
    private MultipartFile file;

    // 줄바꿈을 <br> 태그로 변환하는 메서드
    public String getFormattedContent() {
        return this.content != null ? this.content.replace("\n", "<br>") : null;
    }

    //시간대 변경
    public String getFormattedWdate() {
        ZonedDateTime utcDateTime = this.wdate.toInstant().atZone(ZoneId.of("UTC"));
        return utcDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

}
