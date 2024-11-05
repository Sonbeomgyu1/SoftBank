package com.mysite.sb.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
    private PasswordEncoder passwordEncoder; // PasswordEncoder 주입

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

	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes) {
	    System.out.println("Username: " + username);
	    System.out.println("Password: " + password);
	    
	    UserVo user = userDao.findByUsername(username);
	    if (user != null) {
	        System.out.println("User found: " + user.getUsername());
	        if (passwordEncoder.matches(password, user.getPassword())) {
	            System.out.println("Password matches!");
	            return "redirect:/"; // 로그인 성공
	        } else {
	            System.out.println("Password does not match.");
	        }
	    } else {
	        System.out.println("User not found.");
	    }
	    
	    redirectAttributes.addFlashAttribute("errorMessage", "잘못된 비밀번호입니다.");
	    return "redirect:/login?error"; // 로그인 실패
	}





	// 로그아웃 처리
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout"; // 로그아웃 후 로그인 페이지로 리다이렉트
	}

	@GetMapping("/succ")
	public String succ(HttpSession session, Model model) {
		String username = (String) session.getAttribute("username");
		System.out.println("Current username in session: " + username); // 로그 출력
		model.addAttribute("username", username);
		return "/succ";
	}
}
