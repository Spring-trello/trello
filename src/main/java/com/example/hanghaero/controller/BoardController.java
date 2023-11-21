package com.example.hanghaero.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hanghaero.dto.board.BoardRequestDto;
import com.example.hanghaero.dto.board.BoardResponseDto;
import com.example.hanghaero.security.UserDetailsImpl;
import com.example.hanghaero.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BoardController {
	private final BoardService boardService;

	@PostMapping("/boards")
	public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardRequestDto boardRequestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		Long userId = userDetails.getUser().getId();
		return new ResponseEntity<>(boardService.createBoard(boardRequestDto, userId), HttpStatus.CREATED);
	}

	@PutMapping("/boards/{id}")
	public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable Long id,
		@RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		Long userId = userDetails.getUser().getId();
		return new ResponseEntity<>(boardService.updateBoard(boardRequestDto, userId, id), HttpStatus.OK);
	}

	@DeleteMapping("/boards/{id}")
	public ResponseEntity deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		Long userId = userDetails.getUser().getId();
		boardService.deleteBoard(id, userId);
		return ResponseEntity.status(HttpStatus.OK).body("보드가 삭제되었습니다.");
	}

	@GetMapping("/boards")
	public ResponseEntity inviteBoard(@RequestParam("userId") Long userId,
		@RequestParam("boardId") Long boardId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		Long id = userDetails.getUser().getId();
		boardService.inviteBoard(id, userId, boardId);
		return ResponseEntity.status(HttpStatus.OK).body("초대가 완료되었습니다.");
	}
}
