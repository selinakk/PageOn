package com.tmtb.pageon.webtoon.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmtb.pageon.board.model.BoardVO;
import com.tmtb.pageon.webnovel.model.WebnovelVO;
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
    //웹툰 리스트
    public List<WebtoonVO> getWebtoonList(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return webtoonMapper.getWebtoonList(offset, pageSize , "popular");
    }
    //인기 웹툰 리스트
    public List<WebtoonVO> selectPopularWebtoons(int offset, int pageSize) {
        return webtoonMapper.selectPopularWebtoons(offset, pageSize);
    }
    //제목 검색
    public List<WebtoonVO> searchWebtoonByTitle(String searchWord, int offset, int pageSize, List<String> categories) {
        return webtoonMapper.searchWebtoonByTitle(searchWord, offset, pageSize, categories);
    }
    //작가 검색
    public List<WebtoonVO> searchWebtoonByWriter(String searchWord, int offset, int pageSize,  List<String> categories) {
        return webtoonMapper.searchWebtoonByWriter(searchWord, offset, pageSize,categories);
    }
    //장르 검색
    public List<WebtoonVO> searchWebtoonByCategories(String searchWord, int offset, int pageSize) {
        return webtoonMapper.searchWebtoonByCategories(searchWord, offset, pageSize);
    }

    public List<WebtoonVO> searchMultiCategories(List<String> categories, int offset, int pageSize) {
        return webtoonMapper.searchMultiCategories(categories, offset, pageSize);
    }

    public List<WebtoonVO> getCategories() {
        return webtoonMapper.getCategories();
    }

    public List<WebtoonVO> searchLikeCategories(List<String> likeCategories, int offset, int pageSize) {
        return webtoonMapper.searchLikeCategories(likeCategories, offset, pageSize);
    }
    public int getTotalCountByLikeCategories(List<String> likeCategories) {
        return webtoonMapper.getTotalCountByLikeCategories(likeCategories);
    }

    public int getTotalCountByPopular() {
        return webtoonMapper.getTotalCountByPopular();
    }

    public int getTotalCountByTitle(String searchWord,List<String> categories) {
        return webtoonMapper.getTotalCountByTitle(searchWord,categories);
    }

    public int getTotalCountByWriter(String searchWord, List<String> categories) {
        return webtoonMapper.getTotalCountByWriter(searchWord, categories);
    }

    public int getTotalCountByCategories(String searchWord) {
        return webtoonMapper.getTotalCountByCategories(searchWord);
    }

    public int getTotalCountByMultiCategories(List<String> categories) {
        return webtoonMapper.getTotalCountByMultiCategories(categories);
    }

    public int getTotalCount() {
        return webtoonMapper.getTotalCount();
    }


    public WebtoonVO selectOne(WebtoonVO vo) {
        return webtoonMapper.selectOne(vo);
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
        Set<String> titles = new HashSet<>();

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
                if (webtoons.has("itemList") && webtoons.get("itemList").isArray()) {
                    for (JsonNode node : webtoons.get("itemList")) {
                        String title = node.get("title").asText();
                        if (!titles.contains(title)) {
                            titles.add(title);
                            allWebtoons.add(node);
                        } else {
                            // 중복된 경우 업데이트
                            WebtoonVO existingWebtoon = webtoonMapper.findByTitle(title);
                            WebtoonVO webtoon = objectMapper.convertValue(node, WebtoonVO.class);
                            if (existingWebtoon != null) {
                                webtoon.setItem_id(existingWebtoon.getItem_id());
                                webtoonMapper.updateWebtoon(webtoon);
                            }
                        }
                    }
                }
            } catch (JsonProcessingException e) {
                // 실패한 경우 예외 발생
                throw new RuntimeException("Failed to parse JSON", e);
            }
        }

        // 데이터를 JsonNode 객체로 반환
        return objectMapper.valueToTree(allWebtoons);
    }

    public JsonNode getWebtoonsKakao() {
        // API URL
        String baseUrl = "https://www.kmas.or.kr/openapi/search/bookAndWebtoonList?prvKey=eaa479a1691ab5f1d257cae310412971&viewItemCnt=100&pltfomCdNm=카카오웹툰&pageNo=";

        List<JsonNode> allWebtoons = new ArrayList<>();
        Set<String> titles = new HashSet<>();

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
                if (webtoons.has("itemList") && webtoons.get("itemList").isArray()) {
                    for (JsonNode node : webtoons.get("itemList")) {
                        String title = node.get("title").asText();
                        if (!titles.contains(title)) {
                            titles.add(title);
                            allWebtoons.add(node);
                        } else {
                            // 중복된 경우 업데이트
                            WebtoonVO existingWebtoon = webtoonMapper.findByTitle(title);
                            WebtoonVO webtoon = objectMapper.convertValue(node, WebtoonVO.class);
                            if (existingWebtoon != null) {
                                webtoon.setItem_id(existingWebtoon.getItem_id());
                                webtoonMapper.updateWebtoon(webtoon);
                            }
                        }
                    }
                }
            } catch (JsonProcessingException e) {
                // 실패한 경우 예외 발생
                throw new RuntimeException("Failed to parse JSON", e);
            }
        }

        // 데이터를 JsonNode 객체로 반환
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
                            webtoon.setItem_id(existingWebtoon.getItem_id());
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


//   웹툰 업데이트 날짜 추가

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

    // 날짜 저장
    public void updateWebtoonUpdateDays(JsonNode webtoons) {
        if (!webtoons.isArray()) {
            throw new IllegalArgumentException("Expected an array of webtoons");
        }

        for (JsonNode webtoonNode : webtoons) {
            if (!webtoonNode.has("webtoons") || !webtoonNode.get("webtoons").isArray()) {
                log.error("Expected an array of webtoons in 'webtoons' field for node: {}", webtoonNode);
                continue;
            }

            for (JsonNode node : webtoonNode.get("webtoons")) {
                String webtoonTitle = node.has("title") ? node.get("title").asText() : null;
                if (webtoonTitle == null) {
                    log.error("Title is missing for webtoon node: {}", node);
                    continue;
                }

                WebtoonVO existingWebtoon = webtoonMapper.findByTitle(webtoonTitle);
                if (existingWebtoon != null) {
                    JsonNode updateDaysNode = node.get("updateDays");
                    String updateDays;

                    if (updateDaysNode != null && updateDaysNode.isArray()) {
                        List<String> updateDaysList = new ArrayList<>();
                        for (JsonNode dayNode : updateDaysNode) {
                            updateDaysList.add(dayNode.asText());
                        }
                        updateDays = String.join(",", updateDaysList);
                    } else if (updateDaysNode != null) {
                        updateDays = updateDaysNode.asText();
                    } else {
                        updateDays = "";
                    }

                    existingWebtoon.setUpdate_day(updateDays);
                    existingWebtoon.setLink(node.has("url") ? node.get("url").asText() : null);
                    webtoonMapper.updateWebtoonUpdateDays(existingWebtoon);
                }
            }
        }
    }

    public List<WebtoonVO> getWebtoonRecommendationBycategory(String id, int offset, int pageSize) {
        return webtoonMapper.getWebtoonRecommendationBycategory(id, offset, pageSize);
    }


    public int webtoonGetRecommandationTotalCount(String id) {
        return webtoonMapper.webtoonGetRecommandationTotalCount(id);
    }



}






