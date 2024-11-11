package com.mysite.sb.user;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;


@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String value;

    UserRole(String value) {
        this.value = value;
    }
    
    
    //0.SiteUser에서 엔티티가 존재하는지 확인 
    
    //@Enumerated(EnumType.STRING)
    //@Column(nullable = false) // 필수 설정
   // private UserRole role = UserRole.USER; // 기본값을 USER로 설정
    
    //1.UserRole.java에서 사용자 역할을 정의합니다.
    //2.UserSecurityService.java에서 사용자 인증 정보를 제공합니다. (UserDetailsService 구현)
    //3.SecurityConfig.java에서 Spring Security 설정을 통해 접근 제어를 설정하고 인증/권한을 관리합니다.
}
