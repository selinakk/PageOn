package com.tmtb.pageon.webtoon.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.tmtb.pageon.webtoon.model.WebtoonApiTest;
import com.tmtb.pageon.webtoon.service.WebtoonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WebtoonRestController {

    private final WebtoonService webtoonService;

    public WebtoonRestController(WebtoonService webtoonService) {
        this.webtoonService = webtoonService;
    }

    private static final String API_URL = "https://www.kmas.or.kr/openapi/search/bookAndWebtoonList?prvKey=9d4d0a15eb8ffd1447ba90994ca4617b&pageNo=0&viewItemCnt=100&pltfomCdNm=웹툰";

    @GetMapping("/webtoons")
    public ResponseEntity<JsonNode> getWebtoons() {
        JsonNode result = webtoonService.getWebtoons();
        return ResponseEntity.ok(result);
    }


}
