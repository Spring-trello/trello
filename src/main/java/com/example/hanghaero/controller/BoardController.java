package com.example.hanghaero.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hanghaero.dto.BoardRequestDto;
import com.example.hanghaero.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
	private final BoardService boardService;

	@PostMapping("")
	public ResponseEntity createBoard(@RequestBody BoardRequestDto requestDto) {
		return new ResponseEntity<>(boardService.createBoard(requestDto), HttpStatus.OK);
	}

	@PutMapping("/{boardId}")
	public ResponseEntity updateBoard(@PathVariable Long boardId, @RequestBody BoardRequestDto requestDto) {
		return new ResponseEntity<>(boardService.upDateBoard(boardId, requestDto), HttpStatus.OK);
	}
}
