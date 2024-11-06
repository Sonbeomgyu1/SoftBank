package com.mysite.sb.board;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name= "board")
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name =" author")
	private String author;
	
	/*
	 * @Column(name="is_public", nullable=false) private String isPublic;
	 */
	 @Column(name = "is_public", nullable = false)
	    private int isPublic; // int 타입으로 변경
	
	@Column(name="content", nullable=false)
	private String content;
	
	/*
	 * @Column(name ="view_count", nullable=false) private String viewcount;
	 */
	   @Column(name = "view_count", nullable = false)
	    private long viewCount; // long 타입으로 변경
	   
	   
	   // 동적 번호 필드
	    @Transient  // 데이터베이스에 저장하지 않음
	    private int dynamicNumber;
	    
	    // 직접 추가한 Getter & Setter
	    public int getDynamicNumber() {
	        return dynamicNumber;
	    }

	    public void setDynamicNumber(int dynamicNumber) {
	        this.dynamicNumber = dynamicNumber;
	    }

}
