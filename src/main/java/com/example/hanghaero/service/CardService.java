package com.example.hanghaero.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hanghaero.dto.card.CardCreateRequestDto;
import com.example.hanghaero.dto.card.CardModifyRequestDto;
import com.example.hanghaero.dto.card.CardResponseDto;
import com.example.hanghaero.entity.Board;
import com.example.hanghaero.entity.Card;
import com.example.hanghaero.entity.Col;
import com.example.hanghaero.entity.User;
import com.example.hanghaero.repository.BoardRepository;
import com.example.hanghaero.repository.CardRepository;
import com.example.hanghaero.repository.ColRepository;
import com.example.hanghaero.security.userdetails.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardService {
	private final CardRepository cardRepository;
	private final BoardRepository boardRepository;
	private final ColRepository colRepository;

	public List<CardResponseDto> getCards(Long boardId){
		System.out.println("CardService getCards Method");
		return cardRepository.getCards(boardId).stream().map(CardResponseDto::new).toList();
	}

	public List<Card> getCardsByColumnId(Long columnId){
		return cardRepository.getCardsByColumnId(columnId);
	}

	public CardResponseDto createCard(CardCreateRequestDto requestDto,
		UserDetailsImpl userDetails) {
		User user = userDetails.getUser();
		Col column = colRepository.findById(requestDto.getColumnId()).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 컬럼"));
		Board board = boardRepository.findById(column.getBoard().getId()).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 보드"));

		int pos = lastPosition(requestDto.getColumnId());
		Card card = new Card(requestDto, user, board, column, pos);
		cardRepository.save(card);

		return new CardResponseDto(card);
	}

	@Transactional
	public CardResponseDto updateCard(Long cardId, CardModifyRequestDto requestDto,
		UserDetailsImpl userDetails) {
		Card card = cardRepository.findById(cardId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 카드"));

		if (!authCheck(card, userDetails)) {
			throw new IllegalArgumentException("카드를 수정할 권한이 없습니다.");
		}

		card.update(requestDto);
		cardRepository.save(card);

		return new CardResponseDto(card);
	}

	@Transactional
	public CardResponseDto moveCard(Long cardId, Long toColumnId, int newPosition) {
		Card card = cardRepository.findById(cardId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 카드"));
		Col column = colRepository.findById(toColumnId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 컬럼"));

		// newPosition이 옮기려는 컬럼의 포지션 범위를 벗어나면 수정
		newPosition = Math.max(0, newPosition);
		newPosition = Math.min(lastPosition(toColumnId), newPosition);

		// 카드를 다른 컬럼으로 옮길 경우, 먼저 현재 컬럼에서 옮길 카드를 제외한 나머지 카드의 포지션을 수정
		if (!Objects.equals(card.getColumn().getColumnId(), toColumnId)) {
			List<Card> cardList = cardRepository
				.findAllByPositionGreaterThanAndColumn_ColumnId(card.getPosition(), card.getColumn().getColumnId());
			for (Card c : cardList) {
				c.setPosition(c.getPosition() - 1);
			}
		}

		// 이후 옮길 컬럼 내부의 포지션을 수정
		List<Card> cardList = cardRepository
			.findAllByPositionGreaterThanEqualAndColumn_ColumnId(newPosition, toColumnId);
		for (Card c : cardList) {
			c.setPosition(c.getPosition() + 1);
		}
		card.move(column, newPosition);

		return new CardResponseDto(card);
	}

	@Transactional
	public String deleteCard(Long cardId, UserDetailsImpl userDetails) {
		Card card = cardRepository.findById(cardId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 카드"));

		if (!authCheck(card, userDetails)) {
			throw new IllegalArgumentException("카드를 삭제할 권한이 없습니다.");
		}

		cardRepository.delete(card);

		return "카드가 삭제되었습니다.";
	}

	private boolean authCheck(Card card, UserDetailsImpl userDetails) {
		if (!Objects.equals(userDetails.getUser(), card.getUser()) &&
			!Objects.equals(userDetails.getUser().getRole(), "ADMIN")) {
			return false;
		}
		return true;
	}

	private int lastPosition(Long columnId) {
		int pos = 0;
		Card card = cardRepository.findFirstByColumn_ColumnIdOrderByPositionDesc(columnId).orElse(null);

		if (card != null) {
			pos = card.getPosition() + 1;
		}

		return pos;
	}
}