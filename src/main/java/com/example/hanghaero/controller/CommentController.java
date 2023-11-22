package com.example.hanghaero.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.hanghaero.dto.CommentRequestDto;
import com.example.hanghaero.security.UserDetailsImpl;
import com.example.hanghaero.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CommentController {
	private final CommentService commentService;

	// 댓글 생성
	@PostMapping("/boards/{boardId}/columns/{columId}/cards/{cardId}/comment")
	public ResponseEntity<String> createComment(@PathVariable Long cardId, @RequestBody CommentRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return commentService.createComment(cardId, requestDto, userDetails);
	}

	// 댓글 수정
	@PutMapping("/boards/{boardId}/columns/{columId}/cards/{cardId}/comment/{commentId}")
	public ResponseEntity<String> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return commentService.updateComment(commentId, requestDto, userDetails);
	}

	@DeleteMapping("/boards/{boardId}/columns/{columId}/cards/{cardId}/comment/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable Long commentId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return commentService.deleteComment(commentId, userDetails);
	}
}
