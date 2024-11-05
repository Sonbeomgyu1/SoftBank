package com.mysite.sb.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username,String password,String name) {
        SiteUser user = new SiteUser();
        user.setUsername(username);       
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name); // name 필드 설정
        this.userRepository.save(user);
        return user;
    }
}