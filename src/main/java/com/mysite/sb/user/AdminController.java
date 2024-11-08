package com.mysite.sb.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.sb.board.BoardRepository;

import jakarta.transaction.Transactional;

import java.util.List;

@Controller // 이 클래스는 Spring의 Controller로, HTTP 요청을 처리하는 역할을 합니다.
public class AdminController {

    @Autowired // UserRepository를 자동으로 주입합니다. 이를 통해 데이터베이스에서 사용자 정보를 조회할 수 있습니다.
    private UserRepository userRepository; // 사용자 정보를 관리하는 Repository
    
    @Autowired
    private BoardRepository boardRepository;

    // 사용자 목록을 조회하는 메서드
    @GetMapping("/admin/users") // /admin/users 경로로 GET 요청이 오면 이 메서드가 실행됩니다.
    public String getUsersList(Model model) {
        List<SiteUser> users = userRepository.findAll(); // 사용자 정보를 데이터베이스에서 모두 조회합니다.
        System.out.println("Users: " + users);  // 디버깅용 로그: 조회된 사용자 목록을 출력합니다.
        
        model.addAttribute("users", users); // 사용자 목록을 모델에 추가하여 뷰에서 사용할 수 있도록 전달합니다.
        return "users"; // 사용자 목록을 보여줄 뷰(HTML 페이지)를 반환합니다.
    }

    // 사용자 역할을 업데이트하는 메서드
    @PostMapping("/admin/users/updateRole") // /admin/users/updateRole 경로로 POST 요청이 오면 이 메서드가 실행됩니다.
    public String updateUserRole(@RequestParam Long userId, @RequestParam String role, Model model) {
        // 사용자 ID와 새로운 역할(role)을 파라미터로 받습니다.

        SiteUser user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        // 주어진 ID로 사용자를 조회합니다. 만약 해당 ID의 사용자가 없으면 예외를 발생시킵니다.
        
        user.setRole(role); // 사용자의 역할을 새로운 역할로 변경합니다.
        
        userRepository.save(user); // 변경된 사용자 정보를 데이터베이스에 저장합니다.
        
        List<SiteUser> users = userRepository.findAll(); // 업데이트된 사용자 목록을 다시 조회합니다.
        model.addAttribute("users", users); // 변경된 사용자 목록을 모델에 추가하여 뷰에서 사용할 수 있도록 전달합니다.
        
        return "users"; // 업데이트 후 다시 사용자 목록을 보여줄 뷰로 리디렉션합니다.
    }
    
    @Transactional
    @PostMapping("/admin/users/delete")
    public String deleteUser(@RequestParam Long userId, Model model) {
        // 사용자 ID로 해당 사용자를 조회
    	//userRepository.findById(userId)는 userId를 이용해 데이터베이스에서 해당 사용자를 조회
        SiteUser user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        // 해당 사용자의 게시물 삭제
        //user.getName()을 사용하여 사용자의 이름을 기준으로 게시물을 찾고, 해당 게시물을 삭제합니다.
        boardRepository.deleteByAuthor(user.getName());

        // 사용자 삭제
        userRepository.delete(user);

        // /admin/users 페이지로 리다이렉트
        return "redirect:/admin/users";
    }


    
    
}
