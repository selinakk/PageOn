package com.tmtb.pageon.webtoon.controller;


import com.tmtb.pageon.webtoon.service.WebtoonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RequestMapping
public class WebtoonRestController {

    private final WebtoonService webtoonService;

    public WebtoonRestController(WebtoonService webtoonService) {
        this.webtoonService = webtoonService;
    }

    @GetMapping("/webtoons")
    public ResponseEntity<String> getWebtoons() {
        String result = webtoonService.getWebtoons();
        return ResponseEntity.ok(result);


    }
}
