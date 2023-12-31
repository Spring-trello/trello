package com.example.hanghaero.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hanghaero.dto.card.CardCreateRequestDto;
import com.example.hanghaero.dto.card.CardModifyRequestDto;
import com.example.hanghaero.dto.card.CardMoveRequestDto;
import com.example.hanghaero.dto.card.CardResponseDto;
import com.example.hanghaero.entity.Board;
import com.example.hanghaero.entity.Card;
import com.example.hanghaero.entity.Col;
import com.example.hanghaero.entity.User;
import com.example.hanghaero.entity.UserRoleEnum;
import com.example.hanghaero.exception.entity.AuthorityNotSufficientException;
import com.example.hanghaero.exception.entity.CardNotFoundException;
import com.example.hanghaero.exception.entity.ColumnNotFoundException;
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

	public List<CardResponseDto> getCardsByBoardId(Long boardId) {
		System.out.println("CardService getCards Method");
		return cardRepository.getCards(boardId).stream().map(CardResponseDto::new).toList();
	}

	public List<CardResponseDto> getCardsByColumnId(Long columnId) {
		return cardRepository.getCardsByColumnId(columnId)
			.stream()
			.map(CardResponseDto::new)
			.collect(Collectors.toList());
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
	public CardResponseDto moveCard(Long cardId, Long toColumnId, int newPosition,
		CardMoveRequestDto cardMoveRequestDto) {
		// Card card = cardRepository.findById(cardId).orElseThrow(CardNotFoundException::new);
		Card card = cardRepository.findWithOptimisticLockById(cardId).orElseThrow(CardNotFoundException::new);

		if (!Objects.equals(card.getColumn().getColumnId(), cardMoveRequestDto.getOriginalColumnId())) {
			// 이미 컬럼이 이동되었음,
			throw new RuntimeException("이미 변경되어 요청 처리 불가");
		}
		if (card.getPosition() != cardMoveRequestDto.getOriginalPosition()) {
			throw new RuntimeException("이미 변경되어 요청 처리 불가");
		}

		Col column = colRepository.findById(toColumnId).orElseThrow(ColumnNotFoundException::new);

		// newPosition이 옮기려는 컬럼의 포지션 범위를 벗어나면 수정
		newPosition = Math.max(0, newPosition);
		newPosition = Math.min(lastPosition(toColumnId), newPosition);

		// 먼저 현재 컬럼에서 옮길 카드를 제외한 나머지 카드의 포지션을 수정
		List<Card> cardListCurrent = cardRepository
			.findAllByPositionGreaterThanAndColumn_ColumnId(card.getPosition(), card.getColumn().getColumnId());
		for (Card c : cardListCurrent) {
			c.setPosition(c.getPosition() - 1);
		}

		// 이후 카드를 옮길 컬럼 내부의 포지션을 수정
		List<Card> cardListTarget = cardRepository
			.findAllByPositionGreaterThanEqualAndColumn_ColumnId(newPosition, toColumnId);
		for (Card c : cardListTarget) {
			c.setPosition(c.getPosition() + 1);
		}
		card.move(column, newPosition);

		return new CardResponseDto(card);
	}

	@Transactional
	public CardResponseDto deleteCard(Long cardId, UserDetailsImpl userDetails) {
		Card card = cardRepository.findById(cardId).orElseThrow(CardNotFoundException::new);

		if (!authCheck(card, userDetails)) {
			throw new AuthorityNotSufficientException("카드", "삭제");
		}
		cardRepository.delete(card);

		return new CardResponseDto(card);
	}

	private boolean authCheck(Card card, UserDetailsImpl userDetails) {
		if (Objects.equals(userDetails.getUser().getId(), card.getUser().getId()) ||
			Objects.equals(userDetails.getUser().getRole(), UserRoleEnum.ADMIN)) {
			return true;
		}
		return false;
	}

	private int lastPosition(Long columnId) {
		int pos = 0;
		Card card = cardRepository.findFirstByColumn_ColumnIdOrderByPositionDesc(columnId).orElse(null);

		if (card != null) {
			pos = card.getPosition() + 1;
		}

		return pos;
	}

	public CardResponseDto getCardById(Long cardId) {
		return new CardResponseDto(
			cardRepository.findById(cardId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카드")));
	}
}