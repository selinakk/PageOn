package com.tmtb.pageon.webtoon.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmtb.pageon.board.model.BoardVO;
import com.tmtb.pageon.webtoon.mapper.WebtoonMapper;
import com.tmtb.pageon.webtoon.model.WebtoonApiTest;
import com.tmtb.pageon.webtoon.model.WebtoonVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    //웹툰 목록 가져오기
    public List<WebtoonApiTest> getWebtoons() {
        String url = "https://korea-webtoon-api-cc7dda2f0d77.herokuapp.com/webtoons";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            return objectMapper.convertValue(root.get("webtoons"), objectMapper.getTypeFactory().constructCollectionType(List.class, WebtoonApiTest.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

    }

    //웹툰 db에 저장
    public void saveWebtoonsToDB(List<WebtoonApiTest> webtoons) {
        for (WebtoonApiTest webtoonApiTest : webtoons) {
            webtoonMapper.saveWebtoon(webtoonApiTest);
        }
    }



}
