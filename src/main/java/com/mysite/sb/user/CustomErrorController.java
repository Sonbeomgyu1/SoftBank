package com.mysite.sb.user;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        // 여기서 오류를 로깅하거나 추가적인 처리를 할 수 있습니다.
        return "error";  // error.html 페이지 반환
    }
}
