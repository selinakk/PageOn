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

import java.util.*;

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
    public List<WebtoonVO> searchWebtoonByCategories(String searchWord, int offset, int pageSize) {
        return webtoonMapper.searchWebtoonByCategories(searchWord, offset, pageSize);
    }
    public List<WebtoonVO> getCategories() {
        return webtoonMapper.getCategories();
    }


    public int getTotalCountByTitle(String searchWord) {
        return webtoonMapper.getTotalCountByTitle(searchWord);
    }
    public int getTotalCountByContent(String searchWord) {
        return webtoonMapper.getTotalCountByContent(searchWord);
    }
    public int getTotalCountByCategories(String searchWord) {
        return webtoonMapper.getTotalCountByCategories(searchWord);
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

    public int getTotalCountByFilteredCategories(List<String> categories) {
        return webtoonMapper.getTotalCountByFilteredCategories(categories);
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
        // API URL
        String baseUrl = "https://www.kmas.or.kr/openapi/search/bookAndWebtoonList?prvKey=eaa479a1691ab5f1d257cae310412971&viewItemCnt=100&pltfomCdNm=웹툰&pageNo=";

        List<JsonNode> allWebtoons = new ArrayList<>();

        // 페이지 번호를 0부터 100까지 10씩 증가 + 반복
        for (int pageNo = 0; pageNo <= 100; pageNo += 10) {
            // 현재 페이지 번호를 포함 API URL을 생성
            String apiUrl = baseUrl + pageNo;

            // RestTemplate을 사용하여 API로부터 JSON 문자열 가져옴
            String result = restTemplate.getForObject(apiUrl, String.class);

            try {
                // JSON 문자열을 JsonNode 객체로 변환
                JsonNode webtoons = objectMapper.readTree(result);

                // 변환된 JsonNode 객체를 리스트에 추가
                allWebtoons.add(webtoons);
            } catch (JsonProcessingException e) {
                // 실패한 경우 예외 발생
                throw new RuntimeException("Failed to parse JSON", e);
            }
        }

        //데이터를 JsonNode 객체로 반환
        return objectMapper.valueToTree(allWebtoons);
    }

    public void saveWebtoons(JsonNode webtoons) {
        if (webtoons.isArray()) {
            for (JsonNode webtoonNode : webtoons) {
                if (webtoonNode.has("itemList") && webtoonNode.get("itemList").isArray()) {
                    for (JsonNode node : webtoonNode.get("itemList")) {
                        String webtoonTitle = node.get("title").asText(); // 웹툰 제목을 가져옴
                        WebtoonVO existingWebtoon = webtoonMapper.findByTitle(webtoonTitle);
                        WebtoonVO webtoon = objectMapper.convertValue(node, WebtoonVO.class);

                        if (existingWebtoon != null) {
                            // 기존 웹툰이 존재하면 업데이트
                            webtoon.setNum(existingWebtoon.getNum());
                            webtoonMapper.updateWebtoon(webtoon);
                        } else {
                            // 기존 웹툰이 없으면 새로 저장
                            webtoonMapper.saveWebtoon(webtoon);
                        }
                    }
                } else {
                    // itemList 필드가 없거나 배열이 아닌 경우에 대한 처리
                    System.err.println("Expected an array of webtoons in 'itemList' field for node: " + webtoonNode);
                }
            }
        } else {
            // webtoons가 배열이 아닌 경우에 대한 처리
            throw new IllegalArgumentException("Expected an array of webtoons");
        }
    }

}
