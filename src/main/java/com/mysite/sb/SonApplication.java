package com.mysite.sb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public class SonApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        // Spring Boot 애플리케이션 설정을 지정
        return application.sources(SonApplication.class);
    }

    public static void main(String[] args) {
        // 내장 Tomcat 서버 없이 외부 서버에서 실행할 경우, main 메서드를 통해 Spring Boot 애플리케이션 시작
        SpringApplication.run(SonApplication.class, args);
    }
}
