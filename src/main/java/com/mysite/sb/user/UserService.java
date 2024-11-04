package com.mysite.sb.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.User; // Spring Security User

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder; // 암호화 인코더 주입

    public UserVo create(UserVo userVo) {
        String username = userVo.getUsername();
        String name = userVo.getName();
        String password = userVo.getPassword();

        if (userDao.existsByUsername(username)) {
            throw new IllegalArgumentException("사용자 이름이 이미 존재합니다."); // 중복된 사용자 이름 예외 처리
        }

        UserVo user = new UserVo();
        user.setUsername(username);
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password)); // PasswordEncoder 사용

        try {
            this.userDao.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("사용자 이름이 이미 존재합니다.");
        }

        return user;
    }

    // login 메서드는 Spring Security의 formLogin을 통해 처리됩니다.
    // 이 메서드는 필요없을 수 있습니다.
    public UserVo login(String username, String password) {
        UserVo user = userDao.findByUsername(username); // 사용자 조회

        if (user != null && passwordEncoder.matches(password, user.getPassword())) { // 비밀번호 일치 확인
            System.out.println("로그인 성공: 사용자 이름 = " + username);
            return user; // 로그인 성공
        }

        throw new IllegalArgumentException("잘못된 사용자 이름 또는 비밀번호입니다."); // 로그인 실패 시 예외 처리
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVo user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
        }
        
        return new User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>() // 권한 목록 추가 가능
        );
    }
}
