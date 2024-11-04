package com.mysite.sb.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@Valid UserVo userVo, BindingResult result, Model model) {
        // 입력값 확인
        System.out.println("register 메서드 호출");
        System.out.println("전달된 UserVo 데이터: " + userVo);

        if (result.hasErrors()) {
            System.out.println("유효성 검사 오류 발생: " + result.getAllErrors());
            return "sbsignup"; // 오류가 있을 경우 등록 페이지로 돌아가기
        }
        
        UserVo savedUser = userService.registerUser(userVo);
        System.out.println("회원가입 성공, 저장된 UserVo: " + savedUser);
        model.addAttribute("message", "회원가입이 완료되었습니다!");
        return "main"; // 성공 페이지로 이동
    }
    
    
    // 회원가입페이지 
    @GetMapping("/sbsignup")
    public String showSignUpForm(Model model) {
        model.addAttribute("userVo", new UserVo());
        return "sbsignup"; 
    }
}
