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

import com.example.hanghaero.dto.card.CardCreateRequestDto;
import com.example.hanghaero.security.UserDetailsImpl;
import com.example.hanghaero.service.CardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardController {
	private final CardService cardService;

	// 카드 생성
	@PostMapping("")
	public ResponseEntity<?> createCard(
		@PathVariable Long boardId, @PathVariable Long columnId,
		@RequestBody CardCreateRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return cardService.createCard(boardId, columnId, requestDto, userDetails);
	}

	// 카드 수정
	@PutMapping("/{cardId}")
	public ResponseEntity<?> updateCard(@PathVariable Long cardId, @RequestBody CardCreateRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return cardService.updateCard(cardId, requestDto, userDetails);
	}

	// 카드 이동
	// TODO: 2023-11-25 이동 후 컬럼 내의 카드들간의 위치 변수도 받아와야함
	@PutMapping("/{cardId}/to/{toColumnId}")
	public ResponseEntity<?> moveCard(
		@PathVariable Long cardId, @PathVariable Long toColumnId) {
		return cardService.moveCard(cardId, toColumnId);
	}

	// 카드 삭제
	@DeleteMapping("/{cardId}")
	public ResponseEntity<String> deleteCard(@PathVariable Long cardId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return cardService.deleteCard(cardId, userDetails);
	}
}
