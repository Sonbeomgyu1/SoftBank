package com.mysite.sb.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserVo, Long> {
    // 추가적인 쿼리가 필요하면 여기에 작성
}

