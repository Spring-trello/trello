package com.example.hanghaero.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hanghaero.dto.board.BoardCreateRequestDto;
import com.example.hanghaero.dto.board.BoardModifyRequestDto;
import com.example.hanghaero.dto.board.BoardResponseDto;
import com.example.hanghaero.entity.Board;
import com.example.hanghaero.entity.BoardUser;
import com.example.hanghaero.entity.User;
import com.example.hanghaero.entity.UserRoleEnum;
import com.example.hanghaero.repository.BoardRepository;
import com.example.hanghaero.repository.BoardUserRepository;
import com.example.hanghaero.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	private final BoardRepository boardRepository;
	private final UserRepository userRepository;
	private final BoardUserRepository boardUserRepository;

	public BoardResponseDto createBoard(BoardCreateRequestDto boardRequestDto, Long userId) {
		Optional<User> user = userRepository.findById(userId);
		Board board = new Board(boardRequestDto);
		board.setUser(user.get());
		BoardUser boardUser = new BoardUser(board, user.get());
		boardUserRepository.save(boardUser);
		return new BoardResponseDto(board);
	}

	@Transactional
	public BoardResponseDto updateBoard(BoardModifyRequestDto boardRequestDto, Long userId, Long id) {
		User user = userRepository.findById(userId).orElseThrow(() ->
			new EntityNotFoundException("존재하지않는 유저입니다.")
		);

		Board board = boardRepository.findById(id).orElseThrow(() ->
			new EntityNotFoundException("존재하지않는 보드입니다.")
		);

		if (UserRoleEnum.ADMIN.equals(user.getRole())) {
			board.update(boardRequestDto);
			return new BoardResponseDto(board);
		}

		if (userId != board.getUser().getId()) {
			throw new EntityNotFoundException("보드를 수정 할 수 있는 권한이 없습니다.");
		}
		board.update(boardRequestDto);
		return new BoardResponseDto(board);

	}

	public void deleteBoard(Long id, Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() ->
			new EntityNotFoundException("존재하지않는 유저입니다.")
		);

		Board board = boardRepository.findById(id).orElseThrow(() ->
			new EntityNotFoundException("존재하지않는 보드입니다.")
		);

		if (UserRoleEnum.ADMIN.equals(user.getRole())) {
			boardRepository.delete(board);
			return;
		}

		if (userId != board.getUser().getId()) {
			throw new EntityNotFoundException("보드를 삭제 할 수 있는 권한이 없습니다.");
		}
		boardRepository.delete(board);
	}

	public void inviteBoard(Long id, Long userId, Long boardId) {
		User user = userRepository.findById(id).orElseThrow(() ->
			new EntityNotFoundException("존재하지않는 유저입니다.")
		);

		User inviteUser = userRepository.findById(userId).orElseThrow(() ->
			new EntityNotFoundException("존재하지않는 유저입니다.")
		);

		Board board = boardRepository.findById(boardId).orElseThrow(() ->
			new EntityNotFoundException("존재하지않는 보드입니다.")
		);

		if (UserRoleEnum.ADMIN.equals(user.getRole())) {
			BoardUser boardUser = new BoardUser(board, inviteUser);
			boardUserRepository.save(boardUser);
			return;
		}

		if (user.getId() != board.getUser().getId()) {
			throw new IllegalArgumentException("초대 할 수 있는 권한이 없습니다.");
		}

		BoardUser boardUser = new BoardUser(board, inviteUser);
		boardUserRepository.save(boardUser);
	}
}
