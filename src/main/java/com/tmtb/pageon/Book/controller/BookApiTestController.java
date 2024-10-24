package com.tmtb.pageon.Book.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmtb.pageon.Book.model.BookVO;
import com.tmtb.pageon.Book.mapper.BookMapper;
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
public class BookApiTestController { // 알라딘 api에서 데이터 받아오기 위한 컨트롤러 http://localhost:8081/books/store 링크 접속하면 DB 받아옵니다.

    private final String TTBKey = ""; // 알라딘 API Key
    private final String API_URL = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx";

    @Autowired
    private BookMapper bookMapper;

    private final List<String> categories = Arrays.asList("50930", "50935", "50932", "50933", "50926", "50928", "50931");
    private final List<String> categoryNames = Arrays.asList("과학(SF)", "로맨스", "무협", "액션/스릴러", "추리/미스터리", "판타지", "호러/공포");

    @GetMapping("/books/store")
    public String storeBooks() {
        log.info("storeBooks() 시작...");

        for (int i = 0; i < categories.size(); i++) {
            String categoryId = categories.get(i);
            String categoryName = categoryNames.get(i);

            String url = UriComponentsBuilder.fromHttpUrl(API_URL)
                    .queryParam("ttbkey", TTBKey)
                    .queryParam("QueryType", "Bestseller")
                    .queryParam("Query", categoryName)
                    .queryParam("SearchTarget", "Book")
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

            List<BookVO> books = parseBooks(response, categoryName);

            for (BookVO book : books) {
                int count = bookMapper.checkDuplicateTitle(book.getTitle());
                if (count == 0) {
                    log.info("중복되지 않은 도서 저장 중: {}", book.getTitle());
                    bookMapper.insertBook(book);
                } else {
                    log.info("중복된 도서: {}", book.getTitle());
                }
            }
        }

        return "book/success";
    }

    private List<BookVO> parseBooks(String jsonResponse, String categoryName) {
        log.info("응답 파싱 시작...");

        List<BookVO> books = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // Jackson의 ALLOW_SINGLE_QUOTES 기능을 활성화
            objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            JsonNode itemsNode = rootNode.path("item");
            if (itemsNode.isArray()) {
                for (JsonNode itemNode : itemsNode) {
                    BookVO vo = new BookVO();

                    vo.setType("book");
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

                    log.info("도서 파싱 완료 - 제목: {}, 링크: {}", vo.getTitle(), vo.getLink());

                    books.add(vo);
                }
            } else {
                log.warn("item 배열이 비어 있음.");
            }
        } catch (Exception e) {
            log.error("JSON 응답 파싱 중 오류 발생", e);
        }

        log.info("응답 파싱 완료.");
        return books;
    }
}
