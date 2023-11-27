package com.example.hanghaero.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hanghaero.dto.board.BoardCreateRequestDto;
import com.example.hanghaero.dto.board.BoardModifyRequestDto;
import com.example.hanghaero.dto.board.BoardResponseDto;
import com.example.hanghaero.security.userdetails.UserDetailsImpl;
import com.example.hanghaero.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
	private final BoardService boardService;

	@PostMapping("")
	public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardCreateRequestDto boardRequestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		Long userId = userDetails.getUser().getId();
		return new ResponseEntity<>(boardService.createBoard(boardRequestDto, userId), HttpStatus.CREATED);
	}

	@PutMapping("/{boardId}")
	public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable Long boardId,
		@RequestBody BoardModifyRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		Long userId = userDetails.getUser().getId();
		return new ResponseEntity<>(boardService.updateBoard(boardRequestDto, userId, boardId), HttpStatus.OK);
	}

	@DeleteMapping("/{boardId}")
	public ResponseEntity<?> deleteBoard(@PathVariable Long boardId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		Long userId = userDetails.getUser().getId();
		boardService.deleteBoard(boardId, userId);
		return ResponseEntity.status(HttpStatus.OK).body("보드가 삭제되었습니다.");
	}

	@PostMapping("/{boardId}/invite")
	public ResponseEntity<?> inviteBoard(
		@PathVariable Long boardId,
		@RequestParam("userId") Long userId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		Long id = userDetails.getUser().getId();
		boardService.inviteBoard(id, userId, boardId);
		return ResponseEntity.status(HttpStatus.OK).body("초대가 완료되었습니다.");
	}
}
