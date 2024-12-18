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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;  // UserRepository 의존성 주입
    private final PasswordEncoder passwordEncoder;  // PasswordEncoder 의존성 주입

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

        // UserRole 열거형을 사용하여 권한 설정
        UserRole role = siteUser.getRole();  // 예: "ADMIN", "USER"
        if (role == UserRole.ADMIN) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }

        // Spring Security의 User 객체로 반환 (사용자 정보 + 권한)
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
    }
}

    
    //회원가입했을시 user로 가입됨  관리자로 바꾸고 싶을떄 sql쿼리로 바꾸면됨    
    //UPDATE users SET role = 'ADMIN' WHERE username = '특정사용자이름';
    
    //새로운 사용자에게 role 컬럼을 ADMIN으로 설정하고싶을떄 SQL쿼리로 이렇게 하면됨
    //INSERT INTO users (username, password, name, role) VALUES ('newadmin', '암호', '관리자 이름', 'ADMIN');
    
    //로그인 순서:
    //사용자가 로그인 폼에서 username과 password를 입력하고 로그인 요청.
    //SecurityConfig에서 설정한 로그인 경로(/user/login)로 요청이 들어오면, Spring Security가 **UserSecurityService**에 인증을 요청.
    //UserSecurityService는 데이터베이스에서 **username**에 해당하는 사용자 정보를 조회.
    //UserSecurityService는 passwordEncoder로 암호화된 비밀번호와 사용자가 입력한 비밀번호를 비교.
    //비밀번호가 맞으면 인증이 성공하고, 인증된 사용자는 시스템에 로그인됩니다.



