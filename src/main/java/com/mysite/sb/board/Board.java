package com.mysite.sb.board;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="board")
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name =" author")
	private String author;
	
	@Column(name="is_public", nullable=false)
	private String isPublic;
	
	@Column(name="content", nullable=false)
	private String content;
	
	@Column(name ="view_count", nullable=false)
	private String viewcount;
}
