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

	 //회원정보목록에서 삭제시 게시글도 같이 삭제되는 처리 deleteByAuthor --> AdminController에 관련된 코드 있음
	void deleteByAuthor(String name);
}
