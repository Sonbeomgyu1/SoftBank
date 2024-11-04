package com.mysite.sb.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/sbsignup")
    public String showSignUpForm(Model model) {
        model.addAttribute("userVo", new UserVo());
        return "sbsignup"; 
    }
    
    // 회원가입
    @PostMapping("/create")
    public String register(@Valid UserVo userVo, BindingResult result, Model model) {
        System.out.println("회원가입 메서드 호출됨");
        System.out.println("전달된 UserVo 데이터: " + userVo);

        if (result.hasErrors()) {
            System.out.println("유효성 검사 오류 발생: " + result.getAllErrors());
            model.addAttribute("errorMessages", result.getAllErrors()); // 오류 메시지 추가
            return "sbsignup"; // 오류가 있을 경우 등록 페이지로 돌아가기
        }
        
        try {
            UserVo savedUser = userService.create(userVo);
            System.out.println("회원가입 성공, 저장된 UserVo: " + savedUser);
            model.addAttribute("message", "회원가입이 완료되었습니다!");
            return "main"; // 성공 페이지로 이동
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "sbsignup"; // 중복 사용자 등으로 인해 오류가 발생했을 때
        }
    }

    

    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            UserVo user = userService.login(username, password);
            model.addAttribute("message", "로그인 성공");
            return "redirect:/"; // 성공적으로 로그인한 후 메인 페이지로 리다이렉션
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "login"; // 로그인 실패 시 로그인 페이지로 돌아가기
        }
    }

    // 로그아웃 처리
    @PostMapping("/logout")
    public String logout(Authentication authentication) {
        if (authentication != null) {
            System.out.println("사용자가 로그아웃했습니다: " + authentication.getName());
        }
        return "redirect:/"; // 로그아웃 후 메인 페이지로 리다이렉트
    }
}

