package com.example.hanghaero.service;

import org.springframework.stereotype.Service;

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
}
