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
