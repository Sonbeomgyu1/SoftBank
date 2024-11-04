package com.mysite.sb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
	            .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
	        .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화 (테스트용)
	        .headers((headers) -> headers
	            .addHeaderWriter(new XFrameOptionsHeaderWriter(
	                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
	        .formLogin((formLogin) -> formLogin
	            .loginPage("/login")
	            .defaultSuccessUrl("/"))
	        .logout((logout) -> logout
	            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	            .logoutSuccessUrl("/board")
	            .invalidateHttpSession(true));
	    return http.build();
	}

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}