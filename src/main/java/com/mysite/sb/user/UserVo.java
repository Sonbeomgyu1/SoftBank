package com.mysite.sb.user;

import org.springframework.data.relational.core.mapping.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Table;
import jakarta.persistence.Id;


@Getter
@Setter
@Entity
@Table(name = "users")
public class UserVo {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	 @jakarta.persistence.Column(name = "username") // 경로 지정
	    private String username;

	    @jakarta.persistence.Column(name = "password") // 경로 지정
	    private String password;

	    @jakarta.persistence.Column(name = "name") // 경로 지정
	    private String name;

}
