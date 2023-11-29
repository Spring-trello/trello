package com.example.hanghaero.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hanghaero.dto.comment.CommentCreateRequestDto;
import com.example.hanghaero.dto.comment.CommentModifyRequestDto;
import com.example.hanghaero.dto.comment.CommentResponseDto;
import com.example.hanghaero.security.userdetails.UserDetailsImpl;
import com.example.hanghaero.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
	private final CommentService commentService;

	// 서비스 로직에서 board, column를 알 필요 없다.
	// 댓글 생성
	@PostMapping("")
	public ResponseEntity<?> createComment(
		@RequestBody CommentCreateRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return ResponseEntity.status(201).body(commentService.createComment(requestDto, userDetails));
	}

	// 댓글 수정
	@PutMapping("/{commentId}")
	public ResponseEntity<?> updateComment(
		@PathVariable Long commentId,
		@RequestBody CommentModifyRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return ResponseEntity.ok().body(commentService.updateComment(commentId, requestDto, userDetails));
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable Long commentId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return commentService.deleteComment(commentId, userDetails);
	}

	@GetMapping("/{cardId}")
	public List<CommentResponseDto> getCommentsByCardId(@PathVariable Long cardId) {
		return commentService.getCommentsByCardId(cardId);
	}
}
