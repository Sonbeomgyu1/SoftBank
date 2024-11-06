package com.mysite.sb.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;  // UserRepository 의존성 주입

    // 사용자 이름으로 사용자 정보를 로드하는 메서드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 사용자 정보를 DB에서 조회
        Optional<SiteUser> _siteUser = this.userRepository.findByUsername(username);
        
        // 사용자가 존재하지 않으면 예외 발생
        if (_siteUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        
        // 사용자 정보를 가져옴
        SiteUser siteUser = _siteUser.get();
        
        // 권한 리스트를 생성
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        // 만약 admin이면 ADMIN 권한 부여, 아니면 USER 권한 부여
        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }

        // Spring Security의 User 객체로 반환 (사용자 정보 + 권한)
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
    }
}
