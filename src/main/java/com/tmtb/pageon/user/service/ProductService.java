package com.tmtb.pageon.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ProductService {

    private static final int MAX_RECENT_ITEMS = 4;
    private Map<String, List<Object>> userRecentItems = new HashMap<>(); // 사용자 ID에 따른 최근 본 항목 관리

    // 최근 본 항목 리스트 추가
    @CachePut(value = "recentItems")
    public List<Object> addRecentItem(String id, Object item) {
        log.info("캐싱 들어오는 데이터 확인: {}", item);
        log.info("현재 최근 본 항목 리스트 크기: {}", userRecentItems.getOrDefault(id, new ArrayList<>()).size());

        // 사용자별 최근 본 항목 리스트 가져오기
        List<Object> recentItems = userRecentItems.getOrDefault(id, new ArrayList<>());

        // 중복된 데이터가 있는지 체크
        if (!recentItems.contains(item)) {
            if (recentItems.size() >= MAX_RECENT_ITEMS) {
                recentItems.remove(0);  // 가장 오래된 항목 삭제
                log.info("가장 오래된 항목 삭제 후 리스트 크기: {}", recentItems.size());
            }

            recentItems.add(item);
            log.info("항목 추가 후 최근 본 항목 리스트 크기: {}", recentItems.size());
            userRecentItems.put(id, recentItems); // 사용자별 리스트 업데이트
        } else {
            log.info("중복된 항목으로 추가되지 않음: {}", item);
        }

        return recentItems;
    }

    // 최근 본 항목 조회
    public List<Object> getRecentItems(String userId) {
        return userRecentItems.getOrDefault(userId, new ArrayList<>()); // 사용자별 최근 본 항목 반환
    }
}