package com.spring.training.board.dao;

import java.util.List;

import com.spring.training.board.dto.BoardDto;

public interface BoardDao {

	public void insertBoard(BoardDto boardDto);
	public List<BoardDto> selectListBoard();
	public BoardDto selectOneBoard(int num);
	public void updateReadCount(int num);
	public BoardDto validateUserCheck(BoardDto boardDto);
	public void updateBoard(BoardDto boardDto);
}
