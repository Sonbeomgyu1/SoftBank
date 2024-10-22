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
	//사업분야
	@GetMapping("/business")
	public String business() {
		return "business";
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

}
