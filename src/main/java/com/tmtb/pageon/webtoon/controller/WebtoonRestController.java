package com.tmtb.pageon.webtoon.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.tmtb.pageon.webtoon.service.WebtoonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(WebtoonRestController.class);
    private final WebtoonService webtoonService;

    public WebtoonRestController(WebtoonService webtoonService) {
        this.webtoonService = webtoonService;
    }

    @GetMapping("/naverwebtoons")
    public ResponseEntity<JsonNode> getWebtoonsNaver() {
        JsonNode result = webtoonService.getWebtoonsNaver();
        webtoonService.saveWebtoons(result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/kakaowebtoons")
    public ResponseEntity<JsonNode> getWebtoonsKakao() {
        JsonNode result = webtoonService.getWebtoonsKakao();
        webtoonService.saveWebtoons(result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/updateday")
    public ResponseEntity<JsonNode> updateDay() {
        JsonNode result = webtoonService.getUpdateDay();
        webtoonService.updateWebtoonUpdateDays(result);
        log.info("날짜 업데이트");
        return ResponseEntity.ok(result);
    }


}
