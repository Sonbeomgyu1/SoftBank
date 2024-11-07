package com.mysite.sb.board;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.sb.user.SiteUser;
import com.mysite.sb.user.UserService;

import jakarta.servlet.http.HttpSession;



/*import lombok.extern.slf4j.Slf4j;*/

/*@Slf4j*/
@Controller
@RequestMapping("/board")
public class BoardController {

	// 필드 선언
	private final BoardService boardService;
	//UserService 주입
	private final  UserService userService;

	/* private BoardService boardService; */
	@Autowired
	public BoardController(BoardService boardService , UserService userService) {
		this.boardService = boardService;
		this.userService= userService;
	}

	// board로 접근시 /board/list로 리다이렉트
	@GetMapping
	public String redirectToBoardList() {
		return "redirect:/board/list";
	}

	// 게시글 목록 페이지
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		// 페이징 처리

		/*
		 * Pageable pageable = PageRequest.of(page, size); // 정렬은 Repository에서 처리
		 */

		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
		Page<Board> boards = boardService.getBoardList(pageable);
		/*
		 * log.info("Total Elements: {}", boards.getTotalElements());
		 * log.info("Current Page: {}", boards.getNumber()); log.info("Page Size: {}",
		 * boards.getSize());
		 */

		// 각 페이지의 첫 번째 게시글 부터 1번으로 설정
		/* int totalElements = (int) boards.getTotalElements(); */
		int currentPageStartNumber = (page * size) + 1;

		for (int i = 0; i < boards.getContent().size(); i++) {
			Board board = boards.getContent().get(i);
			board.setDynamicNumber(currentPageStartNumber + i);
			/*
			 * log.info("Board ID: {}, Dynamic Number: {}", board.getId(),
			 * board.getDynamicNumber());
			 */
		}

		/* List<Board> boards = boardService.getAllBoards(); */
		/* System.out.println("Boards : boards"); */
		model.addAttribute("boards", boards);
		return "board"; // board.html

	}

	// 게시글 상세 페이지
	@GetMapping("/detail/{id}")
	public String detail(@PathVariable("id") Long id, Model model, Principal principal, HttpSession session) {
		Board board = boardService.getBoardById(id);

		// 비공개 글일 경우 로그인한 사용자만 접근 허용

		
		  if(board.getIsPublic() == 0 && principal ==null) {
		  
			   
			  
			  session.setAttribute("redirectURL", "/board/detail/" + id);
				 return "redirect:/user/login"; 
		 
		  }
		 
		
		
		// 조회수 증가 로직
		boardService.incrementViewCount(board);

		model.addAttribute("board", board);
		return "boardDetail"; // boardDetail.html
	}

	// 게시글 작성 페이지
	@GetMapping("/write")
	public String writeForm(Model model, Principal principal) {
		
		if (principal != null) {
			//현재 로그인한 사용자의 SiteUser 엔티티를 조회하여 name을 가져옴
			SiteUser user = userService.findByUsername(principal.getName());
			if(user !=null) {
				model.addAttribute("authorName",user.getName());
			}
		}
		
		return "board_write"; // boar_write.html
	}

	// 게시글 작성 처리
	/*
	 * @PostMapping("/save") public String save(Board board) { // 디버깅을 위해 각 필드의 값을
	 * 출력해 봅니다. System.out.println("Board Title: " + board.getTitle());
	 * System.out.println("Board Author: " + board.getAuthor());
	 * System.out.println("Board Content: " + board.getContent());
	 * System.out.println("Board IsPublic: " + board.getIsPublic());
	 * 
	 * board.setIsPublic(board.getIsPublic() != 0 ? board.getIsPublic() : 0);
	 * 
	 * boardService.saveBoard(board); return "redirect:/list"; }
	 */

	/*
	 * @PostMapping("/save") public String save(Board board, @RequestParam(value =
	 * "isPublic", required = false) String isPublicParam) { // "on"이라는 문자열이 전달될 경우
	 * 1로 설정, 그렇지 않으면 0으로 설정 board.setIsPublic("on".equals(isPublicParam) ? 1 : 0);
	 * System.out.println("Author: " + board.getAuthor()); // 추가하여 데이터 확인
	 * boardService.saveBoard(board); return "redirect:/board/list"; return
	 * "redirect:/list"; }
	 */

	// 게시글 작성 처리
	@PostMapping("/save")
	public String save(Board board,
			@RequestParam(value = "isPublic", required = false, defaultValue = "0") int isPublicParam) {
		// 체크박스가 선택되었을때 1로 설정, 선택되지 않으면 기본값 0 사용
		board.setIsPublic(isPublicParam);
		System.out.println("Author:" + board.getAuthor());
		boardService.saveBoard(board);
		return "redirect:/board/list";
	}

	// 게시글 수정 페이지
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable Long id, Model model) {
		Board board = boardService.getBoardById(id);
		model.addAttribute("board", board);
		return "boardUpdateDelete"; // boardUpdateDelete.html
	}

	// 게시글 수정 처리
	/*
	 * @PostMapping("/update") public String update(Board board, @RequestParam(value
	 * = "isPublic", required = false) String isPublicParam) {
	 * System.out.println("isPublicParam:" + isPublicParam); //"on" 라는 값이 전달될 경우 1로
	 * 설정하고, 그렇지 않으면 0으로 설정 int isPublicValue = "on".equals(isPublicParam) ? 1: 0;
	 * System.out.println("isPublicValue: " + isPublicValue);
	 * board.setIsPublic(isPublicValue);
	 * 
	 * board.setIsPublic("on".equals(isPublicParam) ? 1 : 0); // 체크박스에 따라 값 설정
	 * 
	 * boardService.updateBoard(board); return "redirect:/board/detail/" +
	 * board.getId(); }
	 */
	// 게시글 수정처리 (수정중인 코드)
	@PostMapping("/update")
	// isPublic이 int형이기 때문에 String에서 int로 변환/ boardUpdateDelete.html에 <input>태그
	// isPublic 부분 value="1"으로 수정
	public String update(Board board,
			@RequestParam(value = "isPublic", required = false, defaultValue = "0") int isPublicParam) {
		// 체크 여부에 따라 1 또는 0
		board.setIsPublic(isPublicParam);
		boardService.updateBoard(board);
		return "redirect:/board/detail/" + board.getId();

	}

	// 게시글 삭제 처리
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		boardService.deleteBoard(id);
		return "redirect:/board/list";
	}
}
