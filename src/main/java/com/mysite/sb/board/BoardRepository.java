package com.mysite.sb.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
	
	//페이징 처리
	 Page<Board> findAllByOrderByIdDesc(Pageable pageable); 
	/* Page<Board> findAll(Pageable pageable); */
}
