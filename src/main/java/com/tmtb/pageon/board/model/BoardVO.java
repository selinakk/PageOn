package com.tmtb.pageon.board.model;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class BoardVO {

    private Integer num;
    private String title;
    private String content;
    private String user_id;
    private LocalDate wdate;
    private String img_name;
    private Integer report;
    private String category;
    private Integer boardhitcount;
    private MultipartFile file;

}
