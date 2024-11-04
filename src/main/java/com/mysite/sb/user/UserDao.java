package com.mysite.sb.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserVo, Long> {
  Optional<UserVo> findByUsername(String username);
}

