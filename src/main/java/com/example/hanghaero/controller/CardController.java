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

import com.example.hanghaero.dto.CardRequestDto;
import com.example.hanghaero.security.UserDetailsImpl;
import com.example.hanghaero.service.CardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class CardController {
	private final CardService cardService;

	// 카드 생성
	@PostMapping("/{boardId}/columns/{columnId}/cards")
	public ResponseEntity<String> createCard(@PathVariable Long boardId, @PathVariable Long columnId,
		@RequestBody CardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		return cardService.createCard(boardId, columnId, requestDto, userDetails);
	}

	// 카드 수정
	@PutMapping("/{boardId}/columns/{columnId}/cards/{cardId}")
	public ResponseEntity<String> updateCard(@PathVariable Long cardId, @RequestBody CardRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return cardService.updateCard(cardId, requestDto, userDetails);
	}

	// 카드 이동
	@PutMapping("/{boardId}/columns/{columnId}/cards/{cardId}/move/{toColumnId}")
	public ResponseEntity<String> moveCard(@PathVariable Long cardId, @PathVariable Long toColumnId) {
		return cardService.moveCard(cardId, toColumnId);
	}

	// 카드 삭제
	@DeleteMapping("/boards/{boardId}/columns/{columId}/cards/{cardId}")
	public ResponseEntity<String> deleteCard(@PathVariable Long cardId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return cardService.deleteCard(cardId, userDetails);
	}
}
