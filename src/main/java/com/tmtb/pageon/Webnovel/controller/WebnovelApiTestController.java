package com.tmtb.pageon.Webnovel.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmtb.pageon.Webnovel.model.WebnovelVO;
import com.tmtb.pageon.Webnovel.mapper.WebnovelMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
public class WebnovelApiTestController {

    private final String TTBKey = ""; // 알라딘 API Key
    private final String API_URL = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx";

    @Autowired
    private WebnovelMapper webnovelMapper;

    private final List<String> categories = Arrays.asList("139443", "170371", "170373", "170372", "170368", "170369");
    private final List<String> categoryNames = Arrays.asList("로맨틱판타지", "대체역사물", "스포츠물", "정통판타지", "현대판타지", "게임판타지");

    @GetMapping("/webnovels/store")
    public String storeWebnovels() {
        log.info("storeWebnovels() 시작...");

        for (int i = 0; i < categories.size(); i++) {
            String categoryId = categories.get(i);
            String categoryName = categoryNames.get(i);

            String url = UriComponentsBuilder.fromHttpUrl(API_URL)
                    .queryParam("ttbkey", TTBKey)
                    .queryParam("QueryType", "Bestseller")
                    .queryParam("Query", categoryName)
                    .queryParam("SearchTarget", "Webnovel")
                    .queryParam("CategoryId", categoryId)
                    .queryParam("MaxResults", "50")
                    .queryParam("output", "js")
                    .queryParam("Version", "20131101")  // 최신 버전 추가
                    .build().toString();

            log.info("API 요청 URL: {}", url);

            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            // 응답에서 단일 인용부호를 이스케이프 처리
            response = response.replace("'", "\\'");

            List<WebnovelVO> webnovels = parseWebnovels(response, categoryName);

            for (WebnovelVO webnovel : webnovels) {
                int count = webnovelMapper.checkDuplicateTitle(webnovel.getTitle());
                if (count == 0) {
                    log.info("중복되지 않은 웹소설 저장 중: {}", webnovel.getTitle());
                    webnovelMapper.insertWebnovel(webnovel);
                } else {
                    log.info("중복된 웹소설: {}", webnovel.getTitle());
                }
            }
        }

        return "webnovel/success";
    }

    private List<WebnovelVO> parseWebnovels(String jsonResponse, String categoryName) {
        log.info("응답 파싱 시작...");

        List<WebnovelVO> webnovels = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // Jackson의 ALLOW_SINGLE_QUOTES 기능을 활성화
            objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            JsonNode itemsNode = rootNode.path("item");
            if (itemsNode.isArray()) {
                for (JsonNode itemNode : itemsNode) {
                    WebnovelVO vo = new WebnovelVO();

                    vo.setType("webnovel");
                    vo.setTitle(itemNode.path("title").asText());
                    vo.setDesc(itemNode.path("description").asText());
                    vo.setWriter(itemNode.path("author").asText().length() > 100
                            ? itemNode.path("author").asText().substring(0, 100)
                            : itemNode.path("author").asText());
                    vo.setProvider(itemNode.path("publisher").asText().length() > 100
                            ? itemNode.path("publisher").asText().substring(0, 100)
                            : itemNode.path("publisher").asText());

                    vo.setCategories(categoryName);
                    vo.setImg_name(itemNode.path("cover").asText());

                    // 상품 링크 추가
                    vo.setLink(itemNode.path("link").asText());

                    log.info("웹소설 파싱 완료 - 제목: {}, 링크: {}", vo.getTitle(), vo.getLink());

                    webnovels.add(vo);
                }
            } else {
                log.warn("item 배열이 비어 있음.");
            }
        } catch (Exception e) {
            log.error("JSON 응답 파싱 중 오류 발생", e);
        }

        log.info("응답 파싱 완료.");
        return webnovels;
    }
}
