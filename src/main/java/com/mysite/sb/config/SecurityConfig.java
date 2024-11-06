package com.mysite.sb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

@Component
@Configuration // Spring Security 설정 클래스임을 나타냄
@EnableWebSecurity // Spring Security 활성화
public class SecurityConfig {
	
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler; // 로그인 성공 시 커스텀 핸들러 설정

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()) // 모든 경로에 대해 접근 허용
            .csrf((csrf) -> csrf
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // CSRF 토큰을 쿠키에 저장
                    .ignoringRequestMatchers(
                        new AntPathRequestMatcher("/user/login/**"), // login에 대해서는 CSRF 보호 무시
                        new AntPathRequestMatcher("/user/signup/**") // 회원가입에 대해서도 CSRF 보호 무시
                    ))           
            .headers((headers) -> headers
                .addHeaderWriter(new XFrameOptionsHeaderWriter(
                    XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))) // X-Frame-Options 설정 (동일 출처에서만 iframe 허용)
            .formLogin((formLogin) -> formLogin
                    .loginPage("/user/login") // 사용자 정의 로그인 페이지 경로 설정
                    .successHandler(customAuthenticationSuccessHandler) // 로그인 성공 시 실행할 핸들러 설정
                    .defaultSuccessUrl("/")) // 로그인 성공 후 기본 이동할 경로 설정
            .logout((logout) -> logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) // 로그아웃 경로 설정
                    .logoutSuccessUrl("/") // 로그아웃 성공 후 이동할 경로
                    .invalidateHttpSession(true)); // 로그아웃 시 세션 무효화
        
        return http.build(); // SecurityFilterChain 반환
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화 방식으로 BCryptPasswordEncoder 사용
    }
    

    // 이 메서드는 AuthenticationManager를 @Bean으로 정의하여 Spring Security에서 관리
    // AuthenticationManager는 사용자 인증 처리의 핵심 컴포넌트로, 
    // 사용자 이름과 비밀번호를 기반으로 사용자 인증을 수행함.
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {      
        return authenticationConfiguration.getAuthenticationManager();       
    }
}
