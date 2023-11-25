package com.example.hanghaero.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hanghaero.dto.column.ColCreateRequestDto;
import com.example.hanghaero.dto.column.ColResponseDto;
import com.example.hanghaero.entity.Board;
import com.example.hanghaero.entity.Col;
import com.example.hanghaero.repository.BoardRepository;
import com.example.hanghaero.repository.ColRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ColService {
	private final BoardRepository boardRepository;
	private final ColRepository columnRepository;

	public ColResponseDto createColumn(ColCreateRequestDto requestDto) {
		Long boardId = requestDto.getBoardId();
		Board findBoard = findBoard(boardId);
		int position = 0;
		if (columnRepository.lastPosition(boardId) != null) {
			position = columnRepository.lastPosition(boardId);
		}
		return new ColResponseDto(columnRepository.save(
			new Col(findBoard, requestDto, position))
		);
	}

	@Transactional
	public ColResponseDto updateColumn(Long boardId, Long columnId, ColCreateRequestDto requestDto) {
		findBoard(boardId);
		Col findColumnObject = findColumn(columnId);
		try {
			findColumnObject.update(requestDto);
		} catch (Exception e) {
			e.getMessage();
		}
		return new ColResponseDto(findColumnObject);
	}

	@Transactional
	public ResponseEntity deleteColumn(Long boardId, Long columnId) {
		findBoard(boardId);
		Col column = findColumn(columnId);
		try {
			columnRepository.delete(column);
		} catch (Exception e) {
			e.getMessage();
		}
		return ResponseEntity.ok().body("칼럼이 삭제되었습니다.");
	}

	@Transactional
	public Object moveColumn(Long boardId, Long columnId, int newPosition) {
		findBoard(boardId); //보드조회
		Col findColumnObject = findColumn(columnId); //칼럼조회
		System.out.println("현재 칼럼의 위치 = " + findColumnObject.getPosition());
		Col CurrentColumns = columnRepository.getPosition(newPosition);
		if (CurrentColumns != null) {
			try {
				// "중복된 칼럼으로 기존에 위차한 칼럼 바뀔 칼럼과 change");
				CurrentColumns.updatePosition(findColumnObject.getPosition());
			} catch (Exception e) {
				e.getMessage();
			}
		}
		try {
			findColumnObject.updatePosition(newPosition);
		} catch (Exception e) {
			e.getMessage();
		}
		return ResponseEntity.ok().body(columnId + "번 칼럼 이동");
	}

	private Board findBoard(Long boardId) {
		Board board = boardRepository.findById(boardId).orElseThrow(
			() -> new IllegalArgumentException("조회되는 보드가 없습니다.")
		);
		return board;
	}

	private Col findColumn(Long columnId) {
		Col column = columnRepository.findById(columnId).orElseThrow(
			() -> new EntityNotFoundException("칼럼을 찾을 수 없습니다.")
		);
		return column;
	}

}
