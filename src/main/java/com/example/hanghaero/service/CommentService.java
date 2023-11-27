package com.example.hanghaero.service;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hanghaero.dto.comment.CommentCreateRequestDto;
import com.example.hanghaero.dto.comment.CommentModifyRequestDto;
import com.example.hanghaero.dto.comment.CommentResponseDto;
import com.example.hanghaero.entity.Card;
import com.example.hanghaero.entity.Comment;
import com.example.hanghaero.entity.User;
import com.example.hanghaero.entity.UserRoleEnum;
import com.example.hanghaero.exception.entity.comment.CardNotFoundException;
import com.example.hanghaero.repository.CardRepository;
import com.example.hanghaero.repository.CommentRepository;
import com.example.hanghaero.security.userdetails.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CardRepository cardRepository;
	private final CommentRepository commentRepository;

	public CommentResponseDto createComment(CommentCreateRequestDto requestDto,
		UserDetailsImpl userDetails) {
		Card card = cardRepository.findById(requestDto.getCardId()).orElseThrow(CardNotFoundException::new);
		User user = userDetails.getUser();

		Comment comment = new Comment(card, user, requestDto);
		commentRepository.save(comment);

		return new CommentResponseDto(comment);
	}

	@Transactional
	public CommentResponseDto updateComment(Long commentId, CommentModifyRequestDto requestDto,
		UserDetailsImpl userDetails) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 댓글"));

		if (!commentAuthCheck(comment, userDetails)) {
			throw new IllegalArgumentException("댓글을 수정할 권한이 없습니다.");
		}

		comment.update(requestDto);
		commentRepository.save(comment);

		return new CommentResponseDto(comment);
	}

	@Transactional
	public ResponseEntity<String> deleteComment(Long commentId, UserDetailsImpl userDetails) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 댓글"));

		if (!commentAuthCheck(comment, userDetails)) {
			throw new IllegalArgumentException("댓글을 삭제할 권한이 없습니다.");
		}

		commentRepository.delete(comment);

		return ResponseEntity.ok("댓글을 삭제하였습니다.");
	}

	private boolean commentAuthCheck(Comment comment, UserDetailsImpl userDetails) {
		return Objects.equals(userDetails.getUser(), comment.getUser()) ||
			Objects.equals(userDetails.getUser().getRole(), UserRoleEnum.ADMIN);
	}
}
