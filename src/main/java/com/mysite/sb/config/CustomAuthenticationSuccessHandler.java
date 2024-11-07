package com.mysite.sb.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // 로그인 성공 시 콘솔에 로그 출력
		/*
		 * System.out.println("로그인 성공: " + authentication.getName());
		 * 
		 * // 세션에 isLoggedIn 속성 추가 request.getSession().setAttribute("isLoggedIn",
		 * true);
		 * 
		 * // redirectURL 파라미터 확인 String redirectURL =
		 * request.getParameter("redirectURL");
		 * System.out.println("Redirect URL in Success Handler: " + redirectURL);
		 * 
		 * 
		 * // 기본 성공 URL로 리다이렉트 response.sendRedirect("/");
		 * 
		 * // redirectURL이 존재하면 해당 URL로 리다이렉트, 그렇지 않으면 기본 URL로 리다이렉트 if (redirectURL !=
		 * null && !redirectURL.isEmpty()) { response.sendRedirect(redirectURL); } else
		 * { response.sendRedirect("/"); }
		 */
        
    	// 로그인 성공 시 콘솔에 로그 출력
        System.out.println("로그인 성공: " + authentication.getName());

        // 세션에 isLoggedIn 속성 추가
        request.getSession().setAttribute("isLoggedIn", true);
    	
    	  // 세션에서 redirectURL 가져오기
        HttpSession session = request.getSession();
        String redirectURL = (String) session.getAttribute("redirectURL");
        System.out.println("Redirect URL from Session: " + redirectURL);

        // 사용 후 세션에서 제거
        session.removeAttribute("redirectURL");

        if (redirectURL != null && !redirectURL.isEmpty()) {
            response.sendRedirect(redirectURL); // 상세 페이지로 리다이렉트
        } else {
            response.sendRedirect("/"); // 메인 페이지로 리다이렉트
        }
        
        
        
    }
}

