package com.tmtb.pageon.board.model;


import lombok.Data;

import java.time.LocalDate;

@Data
public class BoardVO {

    private Integer id;
    private String title;
    private String content;
    private String user_id;
    private LocalDate wdate;
    private String img_name;
    private Integer report;
    private String category;
    private Integer boardhitcount;

}
