package com.mysite.sb.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

//UserRepository 인터페이스는 SiteUser 엔티티에 대한 데이터베이스 작업을 처리하는 리포지토리입니다.
public interface UserRepository extends JpaRepository<SiteUser, Long> {

 // findByusername 메서드는 SiteUser 엔티티에서 username 필드를 기준으로 사용자 데이터를 조회합니다.
 // 반환값은 Optional<SiteUser> 타입으로, 해당 username을 가진 사용자가 존재하지 않을 경우 Optional.empty()를 반환합니다.
 Optional<SiteUser> findByUsername(String username);
}