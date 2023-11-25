package com.example.hanghaero.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hanghaero.dto.column.ColCreateRequestDto;
import com.example.hanghaero.dto.column.ColModifyRequestDto;
import com.example.hanghaero.dto.column.ColResponseDto;
import com.example.hanghaero.entity.Board;
import com.example.hanghaero.entity.Col;
import com.example.hanghaero.repository.BoardRepository;
import com.example.hanghaero.repository.ColRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ColService {
	private final BoardRepository boardRepository;
	private final ColRepository columnRepository;

	public ColResponseDto createColumn(ColCreateRequestDto requestDto) {
		Long boardId = requestDto.getBoardId();
		Board findBoard = boardRepository.findById(requestDto.getBoardId())
			.orElseThrow(() -> new NullPointerException("해당 보드가 없습니다."));

		int position = 0;
		if (columnRepository.lastPosition(boardId) != null) {
			position = columnRepository.lastPosition(boardId);
		}
		return new ColResponseDto(columnRepository.save(
			new Col(findBoard, requestDto, position))
		);
	}

	@Transactional
	public ColResponseDto updateColumn(Long columnId, ColModifyRequestDto requestDto) {
		// 보드에 있는 칼럼 조회
		Col findColumnObject = findColumn(requestDto.getBoardId(), columnId);
		try {
			findColumnObject.update(requestDto);
		} catch (Exception e) {
			e.getMessage();
		}
		return new ColResponseDto(findColumnObject);
	}

	@Transactional
	public ResponseEntity deleteColumn(Long columnId, Long boardId) {
		// 보드에 있는 칼럼 조회
		Col findColumnObject = findColumn(boardId, columnId);
		try {
			columnRepository.delete(findColumnObject);
		} catch (Exception e) {
			e.getMessage();
		}
		return ResponseEntity.ok().body("칼럼이 삭제되었습니다.");
	}

	@Transactional
	public Object moveColumn(Long columnId, int newPosition, Long boardId) {
		// 보드에서 칼럼 조회
		Col findColumnObject = findColumn(boardId, columnId);
		System.out.println("현재 칼럼의 위치 = " + findColumnObject.getPosition());

		Col toColumns = columnRepository.getPosition(boardId,newPosition);
		if (newPosition <= columnRepository.lastPosition(boardId)) {
			System.out.println("보드의 마지막 위치 = " +  columnRepository.lastPosition(boardId));
			try {
				toColumns.updatePosition(findColumnObject.getPosition());
			} catch (Exception e) {
				e.getMessage();
			}
			try{
				findColumnObject.updatePosition(newPosition);
			} catch (Exception e){
				e.getMessage();
			}
		}
		return ResponseEntity.ok();//.body(columnId + "번 칼럼 " + newPosition + "로 위치 이동");
	}

	private Col findColumn(Long boardId, Long columnId) {
		return columnRepository.findColumnsofBoard(boardId, columnId)
			.orElseThrow(() -> new NullPointerException("해당 칼럼을 찾을 수 없습니다."));
	}

}
