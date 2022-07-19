package com.spring.training.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.training.board.dto.BoardDto;
import com.spring.training.board.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	
	@RequestMapping(value = "/boardWrite", method=RequestMethod.GET)
	public ModelAndView boardWrite() {
		return new ModelAndView("board/bWrite");
	}
	
	@RequestMapping(value = "/boardWrite", method=RequestMethod.POST)
	public ModelAndView boardWrite(BoardDto boardDto) {
		boardService.boardWrite(boardDto);
		return new ModelAndView("redirect:boardList");
	}
	
	@RequestMapping(value = "/boardList", method=RequestMethod.GET)
	public ModelAndView boardList() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/bList");
		mv.addObject("boardList", boardService.boardList()/*db에서 가져오기*/);
		return mv;
	}
	
	@RequestMapping(value = "/boardInfo", method=RequestMethod.GET)
	public ModelAndView boardInfo(@RequestParam("num") int num) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/bInfo");
		mv.addObject("boardDto", boardService.boardInfo(num));
		
		return mv;
	}
	
	
}
