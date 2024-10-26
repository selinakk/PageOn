package com.tmtb.pageon.review.controller;
import com.tmtb.pageon.review.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
@Slf4j
@Controller
public class ReviewController {
    @Autowired
    ReviewService service;
}