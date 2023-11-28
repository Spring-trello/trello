package com.example.hanghaero.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.hanghaero.dto.card.CardCreateRequestDto;
import com.example.hanghaero.dto.card.CardModifyRequestDto;
import com.example.hanghaero.dto.card.CardResponseDto;
import com.example.hanghaero.security.userdetails.UserDetailsImpl;
import com.example.hanghaero.service.CardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardRestController {
	private final CardService cardService;

	// 카드 생성
	@PostMapping("")
	@ResponseBody
	public CardResponseDto createCard(
		@RequestBody CardCreateRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return cardService.createCard(requestDto, userDetails);
	}

	// 카드 수정
	@PutMapping("/{cardId}")
	public ResponseEntity<?> updateCard(
		@PathVariable Long cardId,
		@RequestBody CardModifyRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return ResponseEntity.ok().body(cardService.updateCard(cardId, requestDto, userDetails));
	}

	// 카드 이동
	@PutMapping("/{cardId}/to/{toColumnId}/{newPosition}")
	public ResponseEntity<?> moveCard(
		@PathVariable Long cardId, @PathVariable Long toColumnId, @PathVariable int newPosition) {
		return ResponseEntity.ok().body(cardService.moveCard(cardId, toColumnId, newPosition));
	}

	// 카드 삭제
	@DeleteMapping("/{cardId}")
	public CardResponseDto deleteCard(@PathVariable Long cardId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return cardService.deleteCard(cardId, userDetails);
	}
}
