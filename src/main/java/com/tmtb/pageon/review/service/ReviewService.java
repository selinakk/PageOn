package com.tmtb.pageon.review.service;
import com.tmtb.pageon.review.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ReviewService {
    @Autowired
    ReviewMapper mapper;
}