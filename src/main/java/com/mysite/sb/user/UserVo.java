package com.mysite.sb.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "user") // 테이블 이름을 명시적으로 설정
public class UserVo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본키

    private String username;
    private String password;
    private String name;
    

}
