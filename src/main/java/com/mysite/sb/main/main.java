package com.mysite.sb.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class main {

	// 메인페이지
	@GetMapping("/")
	public String Main() {
		return "main";
	}

	// 회사소개
	@GetMapping("/company")
	public String company() {
		return "company";
	}

	// 사업분야
	@GetMapping("/business")
	public String business() {
		return "business";
	}

	// 채용안내
	@GetMapping("/recruitment")
	public String recruitment() {
		return "recruitment";
	}

	// 찾아오는길
	@GetMapping("/contact")
	public String contact() {
		return "contact";
	}

	// 회사소식
	@GetMapping("/board")
	public String board() {
		return "board";
	}

	// 로그인
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	

	// 헤더
	@GetMapping("/header")
	public String header() {
		return "header";
	}

	// 푸터
	@GetMapping("/footer")
	public String footer() {
		return "footer";
	}
	// 푸터
		@GetMapping("/footerno")
		public String footerno() {
			return "footerno";
		}
	
}
