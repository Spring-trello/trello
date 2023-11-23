package com.example.hanghaero.service;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hanghaero.dto.comment.CommentRequestDto;
import com.example.hanghaero.dto.comment.CommentResponseDto;
import com.example.hanghaero.entity.Card;
import com.example.hanghaero.entity.Comment;
import com.example.hanghaero.exception.entity.comment.CardNotFoundException;
import com.example.hanghaero.repository.CardRepository;
import com.example.hanghaero.repository.CommentRepository;
import com.example.hanghaero.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CardRepository cardRepository;
	private final CommentRepository commentRepository;

	public ResponseEntity<?> createComment(Long cardId, CommentRequestDto requestDto,
		UserDetailsImpl userDetails) {
		Card card = cardRepository.findById(cardId).orElseThrow(CardNotFoundException::new);

		if (!cardAuthCheck(card, userDetails)) {
			return ResponseEntity.status(401).body("권한이 없습니다.");
		}

		Comment comment = new Comment(card, requestDto);
		commentRepository.save(comment);

		return ResponseEntity.ok().body(new CommentResponseDto(comment));
	}

	@Transactional
	public ResponseEntity<?> updateComment(Long commentId, CommentRequestDto requestDto,
		UserDetailsImpl userDetails) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 댓글"));

		if (!commentAuthCheck(comment, userDetails)) {
			return ResponseEntity.status(401).body("권한이 없습니다.");
		}

		comment.update(requestDto);
		commentRepository.save(comment);

		return ResponseEntity.ok().body(new CommentResponseDto(comment));
	}

	@Transactional
	public ResponseEntity<String> deleteComment(Long commentId, UserDetailsImpl userDetails) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 댓글"));

		if (!commentAuthCheck(comment, userDetails)) {
			return ResponseEntity.status(401).body("권한이 없습니다.");
		}

		commentRepository.delete(comment);

		return ResponseEntity.ok("댓글을 삭제하였습니다.");
	}

	private boolean cardAuthCheck(Card card, UserDetailsImpl userDetails) {
		if (!Objects.equals(userDetails.getUser(), card.getUser()) &&
			!Objects.equals(userDetails.getUser().getRole(), "ADMIN")) {
			return false;
		}
		return true;
	}

	private boolean commentAuthCheck(Comment comment, UserDetailsImpl userDetails) {
		if (!Objects.equals(userDetails.getUser(), comment.getUser()) &&
			!Objects.equals(userDetails.getUser().getRole(), "ADMIN")) {
			return false;
		}
		return true;
	}

}
