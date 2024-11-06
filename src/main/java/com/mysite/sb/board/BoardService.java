package com.mysite.sb.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	//모든 게시글 조회
	public List<Board> getAllBoards(){
		return boardRepository.findAll();
	}
	// 특정 게시글 ID로 조회
	
	  public Board getBoardById(Long id) {
		  return boardRepository.findById(id).orElseThrow(() -> new RuntimeException("Board not found")); 
		  }
	 

	//새로운 게시글 저장
	public void saveBoard(Board board) {
		boardRepository.save(board);
	}
	
	// 기존 게시글 업데이트
	public void updateBoard(Board board) {
		//기존 데이터 가져오기
		Board existingBoard = getBoardById(board.getId());
		
		//수정 가능한 필드 업데이트
		existingBoard.setTitle(board.getTitle());
		existingBoard.setContent(board.getContent());
		existingBoard.setIsPublic(board.getIsPublic());
		
		  // 주석 처리된 부분은 상황에 따라 필요할 경우 추가 가능
        /*
        existingBoard.setViewcount(board.getViewcount());
        existingBoard.setAuthor(board.getAuthor());
        */
		
		//변경된 내용 저장
		boardRepository.save(existingBoard);
	}
		//게시글 삭제
		public void deleteBoard(Long id) {
			boardRepository.deleteById(id);
		}
		
		//조회수 증가 메서드 추가
		public void incrementViewCount(Board board) {
			//조회수 증가
			board.setViewCount(board.getViewCount() +1);
			//업데이트 된 내용 저장
			boardRepository.save(board);
		}
		
		//페이지 처리
		public Page<Board> getBoardList(Pageable pageable){
			/* return boardRepository.findAll(pageable); */
			 return boardRepository.findAllByOrderByIdDesc(pageable);
		}
		
}
