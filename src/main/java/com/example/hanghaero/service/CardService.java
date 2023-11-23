package com.example.hanghaero.service;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hanghaero.dto.card.CardRequestDto;
import com.example.hanghaero.dto.card.CardResponseDto;
import com.example.hanghaero.entity.Board;
import com.example.hanghaero.entity.Card;
import com.example.hanghaero.entity.Col;
import com.example.hanghaero.entity.User;
import com.example.hanghaero.repository.BoardRepository;
import com.example.hanghaero.repository.CardRepository;
import com.example.hanghaero.repository.ColRepository;
import com.example.hanghaero.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardService {
	private final CardRepository cardRepository;
	private final BoardRepository boardRepository;
	private final ColRepository colRepository;

	public ResponseEntity<?> createCard(Long boardId, Long columnId, CardRequestDto requestDto,
		UserDetailsImpl userDetails) {
		User user = userDetails.getUser();
		Board board = boardRepository.findById(boardId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 보드"));
		Col column = colRepository.findById(columnId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 컬럼"));

		Card card = new Card(requestDto, user, board, column);
		cardRepository.save(card);
		return ResponseEntity.ok().body(new CardResponseDto(card));
	}

	@Transactional
	public ResponseEntity<?> updateCard(Long cardId, CardRequestDto requestDto,
		UserDetailsImpl userDetails) {
		Card card = cardRepository.findById(cardId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 카드"));

		if (!AuthCheck(card, userDetails)) {
			return ResponseEntity.status(401).body("권한이 없습니다.");
		}

		card.update(requestDto);
		cardRepository.save(card);

		return ResponseEntity.ok().body(new CardResponseDto(card));
	}

	@Transactional
	public ResponseEntity<?> moveCard(Long cardId, Long toColumnId) {
		Card card = cardRepository.findById(cardId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 카드"));
		Col column = colRepository.findById(toColumnId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 컬럼"));

		card.move(column);
		cardRepository.save(card);

		return ResponseEntity.ok().body(new CardResponseDto(card));
	}

	@Transactional
	public ResponseEntity<String> deleteCard(Long cardId, UserDetailsImpl userDetails) {
		Card card = cardRepository.findById(cardId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 카드"));

		if (!AuthCheck(card, userDetails)) {
			return ResponseEntity.status(401).body("권한이 없습니다.");
		}

		cardRepository.delete(card);

		return ResponseEntity.ok("카드가 삭제되었습니다.");
	}

	private boolean AuthCheck(Card card, UserDetailsImpl userDetails) {
		if (!Objects.equals(userDetails.getUser(), card.getUser()) &&
			!Objects.equals(userDetails.getUser().getRole(), "ADMIN")) {
			return false;
		}
		return true;
	}
}