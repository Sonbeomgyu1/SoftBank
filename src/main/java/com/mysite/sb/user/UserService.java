package com.mysite.sb.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor  // Lombok 어노테이션, 필수 생성자를 자동으로 생성
@Service  // Spring의 서비스 컴포넌트로 등록
public class UserService {

    private final UserRepository userRepository;  // UserRepository 의존성 주입
    private final PasswordEncoder passwordEncoder;  // PasswordEncoder 의존성 주입

    // 사용자를 생성하는 메서드
    public SiteUser create(String username, String password, String name) {
        SiteUser user = new SiteUser();  // 새로운 SiteUser 객체 생성
        user.setUsername(username);  // username 필드 설정
        user.setPassword(passwordEncoder.encode(password));  // 비밀번호 암호화 후 설정
        user.setName(name);  // name 필드 설정

        this.userRepository.save(user);  // 생성된 사용자 객체를 데이터베이스에 저장
        return user;  // 저장된 사용자 반환
    }
}
