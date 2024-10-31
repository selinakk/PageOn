package com.tmtb.pageon;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching // 캐시 기능 활성화
public class PageOnApplication {

    public static void main(String[] args) {
        SpringApplication.run(PageOnApplication.class, args);
    }

    //branch test
}
