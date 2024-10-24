package com.tmtb.pageon.webtoon.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmtb.pageon.board.model.BoardVO;
import com.tmtb.pageon.webtoon.mapper.WebtoonMapper;
import com.tmtb.pageon.webtoon.model.WebtoonVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class WebtoonService {


    @Autowired
    private WebtoonMapper webtoonMapper;

    public List<WebtoonVO> getWebtoonList(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return webtoonMapper.getWebtoonList(offset, pageSize);
    }

    public List<WebtoonVO> searchWebtoonByTitle(String searchWord, int offset, int pageSize) {
        return webtoonMapper.searchWebtoonByTitle(searchWord, offset, pageSize);
    }

    public List<WebtoonVO> searchWebtoonWriter(String searchWord, int offset, int pageSize) {
        return webtoonMapper.searchWebtoonWriter(searchWord, offset, pageSize);
    }

    public int getTotalCountByTitle(String searchWord) {
        return webtoonMapper.getTotalCountByTitle(searchWord);
    }

    public int getTotalCountByContent(String searchWord) {
        return webtoonMapper.getTotalCountByContent(searchWord);
    }

    public int getTotalCount() {
        return webtoonMapper.getTotalCount();
    }

    public WebtoonVO selectOne(WebtoonVO vo) {
        return webtoonMapper.selectOne(vo);
    }

    // 필터링 관련
    public List<WebtoonVO> filterByCategories(List<String> categories, int offset, int pageSize) {
        return webtoonMapper.filterByCategories(categories, offset, pageSize);
    }

    public int getTotalCountByCategories(List<String> categories) {
        return webtoonMapper.getTotalCountByCategories(categories);
    }


    //웹툰 api 연동
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public WebtoonService(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
    }


    public JsonNode getWebtoons() {
        String apiUrl = "https://www.kmas.or.kr/openapi/search/bookAndWebtoonList?prvKey=9d4d0a15eb8ffd1447ba90994ca4617b&pageNo=0&viewItemCnt=100&pltfomCdNm=웹툰";
        String result = restTemplate.getForObject(apiUrl, String.class);
        try {
            return objectMapper.readTree(result);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }

    public void saveWebtoons(JsonNode webtoons) {
        if (webtoons.has("itemList") && webtoons.get("itemList").isArray()) {
            List<WebtoonVO> webtoonList = new ArrayList<>();
            for (JsonNode node : webtoons.get("itemList")) {
                WebtoonVO webtoon = objectMapper.convertValue(node, WebtoonVO.class);
                webtoonList.add(webtoon);
            }
            for (WebtoonVO webtoon : webtoonList) {
                webtoonMapper.saveWebtoon(webtoon);
            }
        } else {
            throw new IllegalArgumentException("Expected an array of webtoons in 'itemList' field");
        }
    }


}
