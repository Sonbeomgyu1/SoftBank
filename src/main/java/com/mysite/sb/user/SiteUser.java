package com.mysite.sb.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="users")
public class SiteUser { //데이터베이스 엔티티

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;
    
    private String name;
     
    @Column(nullable = false)  // role 필드는 필수로 설정
    private String role = "USER"; // 기본값을 'USER'로 설정
    
    @Override
    public String toString() {
        return "SiteUser{id=" + id + ", username='" + username + "', name='" + name + "', role='" + role + "'}";
    }
    
    //특징	          UserCreateForm	                 SiteUser
    
    //역할	          폼 데이터 전송용 DTO	             데이터베이스 엔티티 (JPA)
    //주로사용되는위치	사용자로부터 입력받은 값을    	    데이터베이스에서 데이터를 읽고 쓰는 데 사용
    //                서버로 전송할 때 사용
    //연결된테이블	    없음 (DB와 연결되지 않음)	            users 테이블과 연결됨 (JPA 엔티티)
    //주요 필드	  username, password, name        id, username, password, name, role
    //특징	      데이터 전송 및 폼 입력 값 저장	        데이터베이스에 실제로 저장되는 엔티티
}