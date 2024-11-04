package com.mysite.sb.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserVo, Long> {
	
	UserVo findByUsername(String username); // 사용자 이름으로 사용자 조회
	boolean existsByUsername(String username); // 사용자 존재 여부 확인 메서드
	
	
}
