package com.mysite.sb.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;  // 사용자 정보 조회를 위한 Repository

    // /admin/users 경로에서 회원 목록을 조회하여 보여줌
    @GetMapping("/admin/users")
    public String getUsersList(Model model) {
        List<SiteUser> users = userRepository.findAll();  // 모든 사용자 조회
        model.addAttribute("users", users);  // 조회한 사용자 목록을 모델에 추가
        return "users";  // HTML 템플릿 (회원 목록을 보여주는 페이지)
    }
}