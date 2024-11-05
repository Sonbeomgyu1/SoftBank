package com.mysite.sb.user;



import javax.validation.Valid;

import org.apache.catalina.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    
    //회원가입 페이지 
    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }
    //회원가입 등록
    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }
        try {
        userService.create(userCreateForm.getUsername(), 
                 userCreateForm.getPassword(),userCreateForm.getName());
        }catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }
        return "redirect:/";
    }
    
    //로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "login_form";
    }
    
    @PostMapping("/user/login")
    public String loginUser(@ModelAttribute User user) {
        // Login logic
    	 System.out.println("로그인 시도: " + user.getUsername());
        return "redirect:/500"; // or wherever you want to redirect after login
    }
}