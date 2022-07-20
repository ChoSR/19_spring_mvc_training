package com.spring.training.board.service;

import java.util.List;

import com.spring.training.board.dto.BoardDto;

public interface BoardService {
	
	public void boardWrite(BoardDto boardDto);
	public List<BoardDto> boardList();
	public BoardDto boardInfo(int num);
	public boolean modifyBoard(BoardDto boardDto);
	
}
