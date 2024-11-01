package com.tmtb.pageon.webnovel.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmtb.pageon.webnovel.model.WebnovelVO;
import com.tmtb.pageon.webnovel.mapper.WebnovelMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Controller
public class WebnovelDBController {

    @Autowired
    private WebnovelMapper webnovelMapper;

    @GetMapping("/webnovels/store")
    public String storeKakaoWebnovels() {
        log.info("Kakao 웹소설 데이터 저장 시작...");

        // JSON 파일 경로
        String filePath = "D:/Multi26/webnovel/data/novels_data.json";

        try {
            // JSON 파일 읽기
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File(filePath));

            List<WebnovelVO> webnovels = new ArrayList<>();

            // 카테고리별 데이터 파싱
            for (Iterator<String> it = rootNode.fieldNames(); it.hasNext(); ) {
                String categoryName = it.next();
                JsonNode categoryNode = rootNode.get(categoryName);

                if (categoryNode.isArray()) {
                    for (JsonNode itemNode : categoryNode) {
                        WebnovelVO vo = new WebnovelVO();
                        vo.setType("webnovel");
                        vo.setTitle(itemNode.path("title").asText());

                        // 설명 길이 제한 설정
                        String description = itemNode.path("summary").asText();
                        if (description.length() > 1000) {
                            log.info("설명이 너무 긴 웹소설: {}, 설명 길이: {}. 건너뜀.", vo.getTitle(), description.length());
                            continue; // 1000자를 초과하면 저장하지 않음
                        }
                        vo.setDesc(description);

                        vo.setWriter(itemNode.path("author").asText());
                        vo.setProvider(itemNode.path("supplier").asText());
                        vo.setCategories(categoryName);
                        vo.setImg_name(itemNode.path("image_url").asText());
                        vo.setLink(itemNode.path("work_url").asText());

                        webnovels.add(vo);
                    }
                }
            }

            // DB 저장
            for (WebnovelVO webnovel : webnovels) {
                int count = webnovelMapper.checkDuplicateTitle(webnovel.getTitle());
                if (count == 0) {
                    log.info("중복되지 않은 웹소설 저장 중: {}", webnovel.getTitle());
                    webnovelMapper.insertWebnovel(webnovel);
                } else {
                    log.info("중복된 웹소설: {}", webnovel.getTitle());
                }
            }

            log.info("Kakao 웹소설 데이터 저장 완료.");
        } catch (Exception e) {
            log.error("JSON 파일 읽기 및 파싱 중 오류 발생", e);
        }

        return "webnovel/success";
    }

}
