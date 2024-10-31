package com.mysite.sb.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder; // 암호화 인코더 주입

    public UserVo registerUser(UserVo userVo) {
        // 비밀번호 암호화
        userVo.setPassword(passwordEncoder.encode(userVo.getPassword()));

        // 저장
        UserVo savedUser = userDao.save(userVo);
        System.out.println("저장된 UserVo 데이터: " + savedUser);
        
        return savedUser;
    }
}
