package com.example.hanghaero.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hanghaero.dto.CommentRequestDto;
import com.example.hanghaero.security.UserDetailsImpl;
import com.example.hanghaero.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CommentController {
	private final CommentService commentService;

	// 서비스 로직에서 board, column를 알 필요 없다.
	// 댓글 생성
	@PostMapping("/{cardId}/comment")
	public ResponseEntity<String> createComment(
		@PathVariable Long cardId,
		@RequestBody CommentRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return commentService.createComment(cardId, requestDto, userDetails);
	}

	// 댓글 수정
	@PutMapping("/{cardId}/comment/{commentId}")
	public ResponseEntity<String> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return commentService.updateComment(commentId, requestDto, userDetails);
	}

	@DeleteMapping("/{cardId}/comment/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable Long commentId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return commentService.deleteComment(commentId, userDetails);
	}
}
