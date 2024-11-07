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

    @GetMapping("/admin/users")
    public String getUsersList(Model model) {
        List<SiteUser> users = userRepository.findAll();
        System.out.println("Users: " + users);  // 디버깅용 로그 추가
        model.addAttribute("users", users);
        return "users";
    }
    

}