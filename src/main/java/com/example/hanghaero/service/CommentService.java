package com.example.hanghaero.service;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hanghaero.dto.CommentRequestDto;
import com.example.hanghaero.entity.Card;
import com.example.hanghaero.entity.Comment;
import com.example.hanghaero.repository.CardRepository;
import com.example.hanghaero.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CardRepository cardRepository;
	private final CommentRepository commentRepository;

	public ResponseEntity<String> createComment(Long cardId, CommentRequestDto requestDto, UserDetails userDetails) {
		Card card = cardRepository.findById(cardId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 카드"));

		if (!cardAuthCheck(card, userDetails)) {
			return ResponseEntity.status(401).body("권한이 없습니다.");
		}

		Comment comment = new Comment(card, requestDto);
		commentRepository.save(comment);

		return ResponseEntity.ok("댓글을 작성하였습니다.");
	}

	@Transactional
	public ResponseEntity<String> updateComment(Long commentId, CommentRequestDto requestDto, UserDetails userDetails) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 댓글"));

		if (!commentAuthCheck(comment, userDetails)) {
			return ResponseEntity.status(401).body("권한이 없습니다.");
		}

		comment.update(requestDto);
		commentRepository.save(comment);

		return ResponseEntity.ok("댓글을 수정하였습니다.");
	}

	@Transactional
	public ResponseEntity<String> deleteComment(Long commentId, UserDetails userDetails) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 댓글"));

		if (!commentAuthCheck(comment, userDetails)) {
			return ResponseEntity.status(401).body("권한이 없습니다.");
		}

		commentRepository.delete(comment);

		return ResponseEntity.ok("댓글을 삭제하였습니다.");
	}

	private boolean cardAuthCheck(Card card, UserDetails userDetails) {
		if (!Objects.equals(userDetails.getUser(), card.getUser()) &&
			!Objects.equals(userDetails.getUser().getRole(), "ADMIN")) {
			return false;
		}
		return true;
	}

	private boolean commentAuthCheck(Comment comment, UserDetails userDetails) {
		if (!Objects.equals(userDetails.getUser(), comment.getUser()) &&
			!Objects.equals(userDetails.getUser().getRole(), "ADMIN")) {
			return false;
		}
		return true;
	}

}
