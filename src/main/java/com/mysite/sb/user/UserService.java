package com.mysite.sb.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder; // 암호화 인코더 주입

    public UserVo registerUser(UserVo userVo) {
        // 사용자명 중복 확인
        if (userDao.findByUsername(userVo.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자명입니다.");
        }

        // 비밀번호 암호화
        userVo.setPassword(passwordEncoder.encode(userVo.getPassword()));

        // 저장
        try {
            UserVo savedUser = userDao.save(userVo);
            System.out.println("저장된 UserVo 데이터: " + savedUser);
            return savedUser;
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("데이터베이스 오류가 발생했습니다. 관리자에게 문의하세요.", e);
        }
    }
}
