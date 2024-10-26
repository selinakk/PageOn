package com.tmtb.pageon.review.model;

import lombok.Data;

@Data
public class ReviewVO {
    private int num;
    private int work_num;
    private String title;
    private String content;
    private String user_id;
<<<<<<< HEAD
    private String wdate;
=======
    private Data wdate;
>>>>>>> 371fe0bb7fda96b1a331213598a11c8555315570
    private double rating;
    private boolean report;
    private int like;
    private int hate;
}
