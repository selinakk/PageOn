package com.tmtb.pageon.webtoon.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmtb.pageon.board.model.BoardVO;
import com.tmtb.pageon.webtoon.mapper.WebtoonMapper;
import com.tmtb.pageon.webtoon.model.WebtoonVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
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


    public JsonNode getWebtoonsNaver() {
        // API URL
        String baseUrl = "https://www.kmas.or.kr/openapi/search/bookAndWebtoonList?prvKey=eaa479a1691ab5f1d257cae310412971&viewItemCnt=100&pltfomCdNm=네이버웹툰&pageNo=";

        List<JsonNode> allWebtoons = new ArrayList<>();

        // 페이지 번호를 0부터 100까지 100씩 증가 + 3000까지 반복으로 일단 하드코딩
        for (int pageNo = 0; pageNo <= 3000; pageNo += 100) {
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

    public JsonNode getWebtoonsKakao() {
        // API URL
        String baseUrl = "https://www.kmas.or.kr/openapi/search/bookAndWebtoonList?prvKey=eaa479a1691ab5f1d257cae310412971&viewItemCnt=100&pltfomCdNm=카카오웹툰&pageNo=";

        List<JsonNode> allWebtoons = new ArrayList<>();

        // 페이지 번호를 0부터 100까지 100씩 증가 + 1500까지 반복으로 일단 하드코딩
        for (int pageNo = 0; pageNo <= 1500; pageNo += 100) {
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
                        WebtoonVO existingWebtoon = webtoonMapper.findByTitle(webtoonTitle); // 중복된 웹툰이 있는지 확인
                        WebtoonVO webtoon = objectMapper.convertValue(node, WebtoonVO.class); // Json -> WebtoonVO로 변환

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


//   웹툰 업데이트 날짜 추가 - 다해줬는데 안댐

    public JsonNode getUpdateDay() {
        // API URL
        String naverUrl = "https://korea-webtoon-api-cc7dda2f0d77.herokuapp.com/webtoons?provider=NAVER&page=";
        String kakaoUrl = "https://korea-webtoon-api-cc7dda2f0d77.herokuapp.com/webtoons?provider=KAKAO&page=";
        List<JsonNode> allWebtoons = new ArrayList<>();
        int page = 0;
        boolean isLastPage = false;

        while (!isLastPage) {
            // NAVER API 호출
            String naverApiUrl = naverUrl + page + "&perPage=100&sort=ASC";
            String naverResult = restTemplate.getForObject(naverApiUrl, String.class);
            try {
                JsonNode naverWebtoons = objectMapper.readTree(naverResult);
                allWebtoons.add(naverWebtoons);
                isLastPage = naverWebtoons.path("isLastPage").asBoolean(false);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to parse JSON from NAVER", e);
            }

            // KAKAO API 호출
            String kakaoApiUrl = kakaoUrl + page + "&perPage=100&sort=ASC";
            String kakaoResult = restTemplate.getForObject(kakaoApiUrl, String.class);
            try {
                JsonNode kakaoWebtoons = objectMapper.readTree(kakaoResult);
                allWebtoons.add(kakaoWebtoons);
                isLastPage = kakaoWebtoons.path("isLastPage").asBoolean(false);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to parse JSON from KAKAO", e);
            }

            page++;
        }

        // 데이터를 JsonNode 객체로 반환
        return objectMapper.valueToTree(allWebtoons);
    }


    public void updateWebtoonUpdateDays(JsonNode webtoons) {
        if (webtoons.isArray()) {
            for (JsonNode webtoonNode : webtoons) {
                if (webtoonNode.has("title") && webtoonNode.has("updateDays")) {
                    String title = webtoonNode.get("title").asText();
                    List<String> updateDays = objectMapper.convertValue(webtoonNode.get("updateDays"), List.class);

                    WebtoonVO existingWebtoon = webtoonMapper.findByTitle(title);
                    if (existingWebtoon != null) {
                        try {
                            String updateDaysJson = objectMapper.writeValueAsString(updateDays);
                            log.info("날짜 추가: {} 추가함: {}", title, updateDaysJson);
                            existingWebtoon.setUpdate_day(updateDaysJson); // update_day 필드 업데이트
                            webtoonMapper.updateWebtoonUpdateDays(existingWebtoon); // DB 업데이트
                        } catch (JsonProcessingException e) {
                            log.error("Error converting updateDays to JSON string", e);
                        }
                    } else {
                        log.warn("제목: {} 못찾음", title);
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Expected an array of webtoons");
        }
    }
}

