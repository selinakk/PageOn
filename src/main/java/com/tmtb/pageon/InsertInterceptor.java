package com.tmtb.pageon;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class InsertInterceptor implements HandlerInterceptor {
    @Autowired
    private HttpSession session;

    private final RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String sPath = request.getServletPath();
        log.info("{} preHandle()....", sPath);
        log.info("session 로그인 아이디:{}", session.getAttribute("id"));

        if (sPath.equals("/bookshelf/insertOK") || sPath.equals("/forum/write")) {
            if (session.getAttribute("id") == null) {
                requestCache.saveRequest(request, response);
                response.sendRedirect("/user/login");
                return false;
            }
        }
        return true;
    }


}
