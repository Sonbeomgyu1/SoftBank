package com.mysite.sb.handler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle404(Model model) {
        model.addAttribute("errorMessage", "Page not found");
        return "error/404"; // 404.html 템플릿 반환
    }

    @ExceptionHandler(Exception.class)
    public String handle500(Model model) {
        model.addAttribute("errorMessage", "Internal Server Error");
        return "500"; // 500.html 템플릿 반환
    }
}
