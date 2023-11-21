package com.example.hanghaero.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hanghaero.dto.BoardRequestDto;
import com.example.hanghaero.dto.BoardResponseDto;
import com.example.hanghaero.entity.Board;
import com.example.hanghaero.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	private final BoardRepository boardRepository;

	public BoardResponseDto createBoard(BoardRequestDto requestDto) {
		return new BoardResponseDto(boardRepository.save(new Board(requestDto)));
	}

	@Transactional
	public BoardResponseDto upDateBoard(Long boardId, BoardRequestDto requestDto) {
		Board board = boardRepository.findById(boardId).orElseThrow(
			() -> new IllegalArgumentException("해당 보드를 찾을 수 없습니다.")
		);
		board.update(requestDto);
		return new BoardResponseDto(board);
	}
}
