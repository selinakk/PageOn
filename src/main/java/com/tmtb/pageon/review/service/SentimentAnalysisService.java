package com.tmtb.pageon.review.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class SentimentAnalysisService {

    private final String clientId = "";  // 네이버에서 발급받은 Client ID
    private final String clientSecret = "";  // 네이버에서 발급받은 Client Secret
    private final String apiUrl = "https://naveropenapi.apigw.ntruss.com/sentiment-analysis/v1/analyze";

    public String analyzeSentiment(String content) throws Exception {
        RestTemplate restTemplate = new RestTemplate();


        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", clientId);
        headers.set("X-NCP-APIGW-API-KEY", clientSecret);
        headers.set("Content-Type", "application/json");

        log.info("headers:{}",headers );

        // 요청 본문 설정
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("content", content);  // 분석할 텍스트

        log.info("requestBody:{}",requestBody );

        // 요청 엔티티 구성 (헤더와 본문)
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        log.info("requestEntity:{}",requestEntity );

        // API 호출
        ResponseEntity<String> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        log.info("response:{}",response );

        // 응답 처리
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("response.getBody():{}",response.getBody() );

            //결과값만 return
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response.getBody());
            String sentiment = root.path("document").path("sentiment").asText();
            return "감정 분석 결과: " + sentiment;
        } else {
            log.error("API 호출 실패: " + response.getStatusCode());
            throw new Exception("API 호출 실패: " + response.getStatusCode());
        }
    }
}
