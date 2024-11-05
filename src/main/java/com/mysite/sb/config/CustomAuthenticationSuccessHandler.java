package com.mysite.sb.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // 로그인 성공 시 콘솔에 로그 출력
        System.out.println("로그인 성공: " + authentication.getName());

        // 기본 성공 URL로 리다이렉트
        response.sendRedirect("/"); // 또는 원하는 경로로 변경
    }
}
