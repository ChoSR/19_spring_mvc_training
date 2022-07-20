package com.spring.training.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.training.board.dao.BoardDao;
import com.spring.training.board.dto.BoardDto;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardDao boardDao;
	
	@Override
	public void boardWrite(BoardDto boardDto) {
		// dao로 전달
		boardDao.insertBoard(boardDto);
	}

	@Override
	public List<BoardDto> boardList() {
		return boardDao.selectListBoard();
	}

	@Override
	public BoardDto boardInfo(int num) {
		boardDao.updateReadCount(num);
		return boardDao.selectOneBoard(num);
	}

	@Override
	public boolean modifyBoard(BoardDto boardDto) {

		boolean isUpdate = false;
		
		if(boardDao.validateUserCheck(boardDto) != null) {
			boardDao.updateBoard(boardDto);
			isUpdate = true;
		}
		
		return isUpdate;
	}
	
	
	
}
