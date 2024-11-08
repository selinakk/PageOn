package com.tmtb.pageon.webtoon.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.tmtb.pageon.webtoon.service.WebtoonService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView getWebtoonsNaver() {
        JsonNode result = webtoonService.getWebtoonsNaver();
        webtoonService.saveWebtoons(result);
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/kakaowebtoons")
    public ModelAndView getWebtoonsKakao() {
        JsonNode result = webtoonService.getWebtoonsKakao();
        webtoonService.saveWebtoons(result);
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/updateday")
    public ModelAndView updateDay() {
        JsonNode result = webtoonService.getUpdateDay();
        webtoonService.updateWebtoonUpdateDays(result);
        log.info("날짜 업데이트");
        return new ModelAndView("redirect:/");
    }


}
