package com.mysite.sb.user;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateFormVo { //회원가입 Vo
    
    private String username;

    private String password;
   
    private String name;
    
     //특징	          UserCreateForm	                 SiteUser
    
    //역할	          폼 데이터 전송용 DTO	             데이터베이스 엔티티 (JPA)
    //주로사용되는위치	사용자로부터 입력받은 값을    	    데이터베이스에서 데이터를 읽고 쓰는 데 사용
    //                서버로 전송할 때 사용
    //연결된테이블	    없음 (DB와 연결되지 않음)	            users 테이블과 연결됨 (JPA 엔티티)
    //주요 필드	  username, password, name	        id, username, password, name
    //특징	      데이터 전송 및 폼 입력 값 저장	        데이터베이스에 실제로 저장되는 엔티티
    
    //회원가입 순서:
    //사용자가 회원가입 폼에서 정보를 입력하고 제출.
    //UserController는 사용자가 입력한 정보를 UserCreateForm으로 받아 UserService의 create() 메서드를 호출.
    //UserService는 받은 데이터를 passwordEncoder로 암호화한 후 UserRepository에 저장.
    //UserRepository는 SiteUser 엔티티를 데이터베이스에 저장.
    //회원가입이 완료되면, 사용자는 로그인을 할 수 있게 됨.
}