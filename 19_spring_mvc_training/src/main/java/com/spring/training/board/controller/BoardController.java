package com.spring.training.board.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.training.board.dto.BoardDto;
import com.spring.training.board.service.BoardService;

@Controller
@RequestMapping("board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	
	@RequestMapping(value = "/boardWrite", method=RequestMethod.GET)
	public ModelAndView boardWrite() {
		return new ModelAndView("board/bWrite");
	}
	
	@RequestMapping(value = "/boardWrite", method=RequestMethod.POST)
	public ModelAndView boardWrite(BoardDto boardDto) throws Exception {
		boardService.boardWrite(boardDto);
		return new ModelAndView("redirect:/board/boardList"); // redirect 에서는 contextPath를 제외하고 url경로를 입력한다.
	}
	
	@RequestMapping(value = "/boardList", method=RequestMethod.GET)
	public ModelAndView boardList() throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/bList");
		mv.addObject("boardList", boardService.boardList()/*db에서 가져오기*/);
		return mv;
	}
	
	@RequestMapping(value = "/boardInfo", method=RequestMethod.GET)
	public ModelAndView boardInfo(@RequestParam("num") int num) throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/bInfo");
		mv.addObject("boardDto", boardService.boardInfo(num));
		
		return mv;
	}
	
	@RequestMapping(value = "/boardUpdate", method=RequestMethod.GET)
	public ModelAndView boardUpdate(@RequestParam("num") int num) throws Exception{
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/bUpdate");
		mv.addObject("boardDto", boardService.boardInfo(num));
		
		return mv;
	}
	
	@RequestMapping(value = "/boardUpdate", method=RequestMethod.POST)
	public @ResponseBody String boardUpdate(BoardDto boardDto, HttpServletRequest request) throws Exception{
		
		String jsScript = "";
		if(boardService.modifyBoard(boardDto)) {
			jsScript =  "<script>";
			jsScript += "alert('It is changed');";
			jsScript += "location.href='"+ request.getContextPath() + "/board/boardList'";
			jsScript += "</script>";
		}
		else {
			jsScript =  "<script>";
			jsScript += "alert('Check your password');";
			jsScript += "history.go(-1)";
			jsScript += "</script>";
		}
		return jsScript;
		
	}
	
	@RequestMapping(value = "/boardDelete", method=RequestMethod.GET)
	public ModelAndView boardDelete(@RequestParam("num") int num) throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/bDelete");
		mv.addObject("boardDto", boardService.boardInfo(num));
		
		
		return mv;
	}
	
	@RequestMapping(value = "/boardDelete", method=RequestMethod.POST)
	public @ResponseBody String boardDelete(BoardDto boardDto, HttpServletRequest request) throws Exception{
		
		String jsScript = "";
		if(boardService.removeBoard(boardDto)) {
			jsScript =  "<script>";
			jsScript += "alert('It has been deleted..');";
			jsScript += "location.href='"+ request.getContextPath() + "/board/boardList'";
			jsScript += "</script>";
		}
		else {
			jsScript =  "<script>";
			jsScript += "alert('Check your password');";
			jsScript += "history.go(-1)";
			jsScript += "</script>";
		}
		return jsScript;
		
	}
}
