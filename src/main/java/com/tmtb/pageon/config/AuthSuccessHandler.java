// AuthSuccessHandler.java
package com.tmtb.pageon.config;

import com.tmtb.pageon.user.model.UserVO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private RequestCache requestCache = new HttpSessionRequestCache();

    public AuthSuccessHandler() {
        super.setRequestCache(requestCache);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // 로그인한 사용자 정보를 로그로 출력
        String id = authentication.getName();
        log.info("로그인된 사용자: " + id);
        // 세션에 사용자 ID 담기
        session.setAttribute("id", id);

        // CustomUser 객체로부터 UserVO 추출
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        UserVO userVO = customUser.getUserVO();

        // 세션에 name 저장
        String name = userVO.getName();
        session.setAttribute("name", name);

        log.info("로그인된 사용자 name: " + name);


        // 요청 캐시 확인
        SavedRequest cashed = requestCache.getRequest(request, response);

        if (cashed == null) {
            // 로그인 환영 페이지로 forward 이동
            RequestDispatcher rd = request.getRequestDispatcher("/user/login_success");
            rd.forward(request, response);
        } else {
            // 원래 가려던 목적지로 리다이렉트
            session.setAttribute("cashed", cashed.getRedirectUrl());
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
