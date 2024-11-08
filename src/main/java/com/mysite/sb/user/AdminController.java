package com.mysite.sb.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;  // 사용자 정보 조회를 위한 Repository

    @GetMapping("/admin/users")
    public String getUsersList(Model model) {
        List<SiteUser> users = userRepository.findAll();
        System.out.println("Users: " + users);  // 디버깅용 로그 추가
        model.addAttribute("users", users);
        return "users";
    }
    // 역할 업데이트 처리
    @PostMapping("/admin/users/updateRole")
    public String updateUserRole(@RequestParam Long userId, @RequestParam String role, Model model) {
        // 사용자 조회
        SiteUser user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        
        // 역할 업데이트
        user.setRole(role);
        
        // 업데이트된 사용자 저장
        userRepository.save(user);
        
        // 변경된 사용자 목록을 모델에 추가
        List<SiteUser> users = userRepository.findAll();
        model.addAttribute("users", users);
        
        return "users"; // 다시 사용자 목록을 출력하는 페이지로 리디렉션
    }

}