package com.itwillbs.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itwillbs.domain.BoardVO;
import com.itwillbs.domain.Criteria;
import com.itwillbs.domain.PageVO;
import com.itwillbs.service.BoardService;

@Controller
@RequestMapping(value = "/board/*")
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	// 서비스객체 주입
	@Inject
	private BoardService bService;
	
	// http://localhost:8088/board/regist
	
	// 게시판 글쓰기 - GET
	@RequestMapping(value = "/regist",method = RequestMethod.GET)
	public void registGET() throws Exception{
		logger.debug(" 게시판 글쓰기 GET - 사용자의 정보 입력 ");
		logger.debug(" 연결된 view 페이지 이동 ");
	}
	
	// 게시판 글쓰기 - POST
	@RequestMapping(value = "/regist",method = RequestMethod.POST)
	public String registPOST(BoardVO vo,RedirectAttributes rttr) throws Exception{
		logger.debug(" 게시판 글쓰기 POST - 입력된 데이터 처리 ");
		// 한글 인코딩(필터 처리)
		// 전달정보 저장
		logger.debug(" vo : " + vo);
		
		// 서비스 -> DAO에 동작 호출
		bService.regist(vo);
		
		// 글쓰기 성공정보를 전달
		rttr.addFlashAttribute("msg", "createOk");
		
		// 페이지 이동
		return "redirect:/board/listALL";
//		return "redirect:/board/listALL?msg=createOK";
//		return "/board/list";
	}
	
	// * 정보조회 동작, 사용자 정보 입력 => GET 방식
	// * 정보를 처리하는 동작(UPDATE,DELETE,INSERT) => POST방식
	
	// 게시판 글목록 조회 - GET
//	@RequestMapping(value = "",method = RequestMethod.GET)
	@GetMapping(value = "/listALL")
	public String listALLGET(Model model) throws Exception {
		logger.debug(" listALLGET() 실행 ");
		
		List<BoardVO> boardList = bService.listALL();
		logger.debug(" size : " + boardList.size());
		
		model.addAttribute("boardList", boardList);
		
		return "/board/list";
	}
	
	//http://localhost:8088/board/read?bno=9
	
	// 게시판 본문보기 - readGET
	@RequestMapping(value = "/read",method = RequestMethod.GET)
	public void readGET(@ModelAttribute("bno") int bno, Model model) throws Exception {
		// @ModelAttribute("bno") int bno
		// => 주소줄에 있는 데이터를 가져와서 사용, 연결된 뷰페이지로 이동 $ {bno}
		//	  request.getParameter("bno") + request.setAttribute();
		// => 1:N 관계에서 사용 (N - bean(객체), collection)
		
		// @RequestParam("bno") int bno
		// => request.getParameter("bno") 동일함, 자동형변환 포함(문자,숫자,날짜)
		// => 1:1 관계에서 사용
		
		logger.debug(" readGET() 실행 ");
		
		// 전달정보 저장
		logger.debug(" bno : " + bno);
		
		// 글 조회(읽음) 카운트 증가 => 조회수 1증가
		bService.updateReadCnt(bno);
		
		// 서비스 - DAO 저장된 정보를 가져오기
		BoardVO resultVO = bService.getBoard(bno);
		logger.debug(" resultVO : {}", resultVO);
		
		// 전달할 정보를 저장(model)
		model.addAttribute("resultVO", resultVO);
		
		// 연결된 뷰페이지 이동
		
		
		
	}
	
	
	// 게시판 글 수정하기(기존의 글정보 확인) - GET
	@RequestMapping(value = "/modify",method = RequestMethod.GET)
	public String modifyGET(Model model,@RequestParam("bno") int bno/* @ModelAttribute */) throws Exception{
		// 적은양 : @RequestParam , 많은양 : @ModelAttribute
		logger.debug(" modifyGET() 실행 ");
		
		// 전달정보 bno 저장
		logger.debug(" bno : " + bno);
		
		// 서비스 - DAO 글정보 조회 동작
		BoardVO resultVO = bService.getBoard(bno);
		logger.debug("resultVO : {}",resultVO);
		
		// 연결된 뷰페이지로 정보 전달
		model.addAttribute("resultVO", resultVO);  
		
		//model.addAttribute("resultVO", bService.getBoard(bno));
		// BoardVO resultVO = bService.getBoard(bno); 와
		// model.addAttribute("resultVO", resultVO); 가 결합된 형태 
		
		// /board/modify.jsp
		return "/board/modify";
	}
	
	// 게시판 글 수정하기(글정보 수정) - POST
	@RequestMapping(value = "/modify",method = RequestMethod.POST)
	public String modifyPOST(BoardVO vo,RedirectAttributes rttr) throws Exception{
		logger.debug(" modifyPOST() 실행 ");
		// 한글처리 인코딩(필터)
		// 전달정보 저장
		logger.debug("수정할 내용",vo);
		
		// 서비스 - DAO 글내용을 수정
		bService.updateBoard(vo);
		
		// 상태 정보 전달
		rttr.addFlashAttribute("msg", "updateOK");
		
		// 페이지 이동(listALL.jsp) 
		return "redirect:/board/listALL";
	}
	
	// 게시판 글 삭제하기 - POST
	@RequestMapping(value = "/remove",method = RequestMethod.POST)
	public String removePOST(RedirectAttributes rttr,@RequestParam("bno") int bno) throws Exception {
		logger.debug("removePOST() 실행");
		
		logger.debug(" 삭제할 글 번호 : {} ", bno);
		
		// 서비스 - DAO 글 삭제 동작
		bService.deleteBoard(bno);
		
		// 전달정보 전달
		rttr.addFlashAttribute("msg", "deleteOK");
		
		// 페이지 이동
		return "redirect:/board/listALL";
	}
	
	@GetMapping(value = "/listPage")
	public String listPageGET(Criteria cri,Model model) throws Exception {
		logger.debug(" listPageGET() 실행 ");
//		페이징 처리 정보 객체
//		Criteria cri = new Criteria();
//		cri.setPage(5);
//		cri.setPageSize(10);
		
		// 서비스 -> DB의 정보를 가져오기 (페이징처리)
		List<BoardVO> boardList = bService.listPage(cri);
		logger.debug(" size : " + boardList.size());
		
		// 하단 페이징처리 정보객체 생성
		PageVO pageVO = new PageVO();
		pageVO.setCri(cri);
		pageVO.setTotalCount(bService.getTotalCount());
		
		// 연결된 뷰페이지로 정보 전달
		model.addAttribute("boardList", boardList);
		model.addAttribute("pageVO", pageVO);
		
		return "/board/list";
	}
	
	
	
	
	
	
	
}
